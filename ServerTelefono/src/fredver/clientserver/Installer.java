package fredver.clientserver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Installer {

	/**
	 * Generates a self destructing OS-specific installation script and executes it
	 * @param args Unused
	 */
	public static void main(String[] args) {
        @SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

        String os = System.getProperty("os.name").toLowerCase();
        boolean isWindows = os.contains("win");
        boolean isMac = os.contains("mac");
        boolean isLinux = os.contains("nix") || os.contains("nux");

        if (!isWindows && !isMac && !isLinux) {
            System.out.println("Unsupported operating system. Exiting installer.");
            return;
        }

        System.out.println("Enter the installation directory where client/server JARs will be saved:");
        String installDirPath = scanner.nextLine();
        File installDir = new File(installDirPath);

        if (!installDir.exists()) {
            if (!installDir.mkdirs()) {
                System.out.println("Failed to create installation directory. Please check permissions and try again.");
                return;
            }
        } else if (!installDir.isDirectory()) {
            System.out.println("The specified path is not a directory. Exiting installer.");
            return;
        }

        int choice = 0;
        boolean validChoice = false;

        while (!validChoice) {
            System.out.println("Select installation option:");
            System.out.println("1: Install Client only");
            System.out.println("2: Install Server only");
            System.out.println("3: Install both Client and Server");

            try {
                choice = Integer.parseInt(scanner.nextLine());

                if (choice >= 1 && choice <= 3) {
                    validChoice = true;
                } else {
                    System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number (1, 2, or 3).");
            }
        }

        try {
            if (isWindows) {
                File scriptFile = generateWindowsScript(installDir, choice);
                executeScript(scriptFile);
            } else if (isMac || isLinux) {
                File scriptFile = generateUnixScript(installDir, choice);
                executeScript(scriptFile);
            }
            System.out.println("Installation script created and executed successfully in " + installDir.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error writing or executing the build script: " + e.getMessage());
        }
    }
	
    /**
     * Generates a self-destructing script for windows users that installs the requested jar files
     * @param installDir The directory to install to
     * @param choice The type of installation.<br> -1 If client;<br> -2 If server;<br> -3 if both
     * @throws IOException if the call to {@link #writeFile(File, String)} throws an IOException
     * @see #writeFile(File, String)
     * @return The File object representing the generated script
     */
    private static File generateWindowsScript(File installDir, int choice) throws IOException {
        StringBuilder scriptContent = new StringBuilder();
        scriptContent.append("@echo off\n")
                .append("set REPO_URL=https://github.com/yourusername/yourproject.git\n")
                .append("echo Cloning the repository...\n")
                .append("git clone %REPO_URL%\n")
                .append("cd yourproject || exit /b\n")
                .append("echo Running Maven build...\n")
                .append("mvn clean package\n")
                .append("echo Build completed.\n");

        if (choice == 1 || choice == 3) {
            scriptContent.append("copy target\\client.jar ").append(installDir.getAbsolutePath()).append("\\client.jar\n");
        }
        if (choice == 2 || choice == 3) {
            scriptContent.append("copy target\\server.jar ").append(installDir.getAbsolutePath()).append("\\server.jar\n");
        }

        // Add self-delete commands
        scriptContent.append("(echo @echo off\n")
                .append("echo del \"%~f0\") > \"%temp%\\delete_self.bat\"\n")
                .append("start \"\" /b \"%temp%\\delete_self.bat\"\n");

        File scriptFile = new File(installDir, "build.bat");
        writeFile(scriptFile, scriptContent.toString());
        return scriptFile;
    }
    
    /**
     * Generates a self-destructing script for unix users that installs the requested jar files
     * @param installDir The directory to install to
     * @param choice The type of installation.<br> -1 If client;<br> -2 If server;<br> -3 if both
     * @throws IOException if the call to {@link #writeFile(File, String)} throws an IOException
     * @see #writeFile(File, String)
     * @return The File object representing the generated script
     */
    private static File generateUnixScript(File installDir, int choice) throws IOException {
        StringBuilder scriptContent = new StringBuilder();
        scriptContent.append("#!/bin/bash\n")
                .append("REPO_URL=https://github.com/yourusername/yourproject.git\n")
                .append("echo \"Cloning the repository...\"\n")
                .append("git clone \"$REPO_URL\"\n")
                .append("cd yourproject || exit\n")
                .append("echo \"Running Maven build...\"\n")
                .append("mvn clean package\n")
                .append("echo \"Build completed.\"\n");

        if (choice == 1 || choice == 3) {
            scriptContent.append("cp target/client.jar ").append(installDir.getAbsolutePath()).append("/client.jar\n");
        }
        if (choice == 2 || choice == 3) {
            scriptContent.append("cp target/server.jar ").append(installDir.getAbsolutePath()).append("/server.jar\n");
        }

        // Add self-delete command
        scriptContent.append("rm -- \"$0\"\n");

        File scriptFile = new File(installDir, "build.sh");
        writeFile(scriptFile, scriptContent.toString());
        scriptFile.setExecutable(true);  // Make executable
        return scriptFile;
    }

    /**
     * Utility function to write content to a file
     * @param file The file to write to
     * @param content The content to write to the file
     * @throws IOException If the writing operation fails
     */
    private static void writeFile(File file, String content) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
            System.out.println("Script file created: " + file.getAbsolutePath());
        } catch (IOException e) {
            throw new IOException("Failed to write to " + file.getAbsolutePath() + ": " + e.getMessage(), e);
        }
    }
    
    /**
     * Utility method to run the batch or bash file for windows or unix-like systems respectively
     * @param scriptFile The File object to represent the script
     */
    private static void executeScript(File scriptFile) {
        ProcessBuilder processBuilder;

        if (scriptFile.getName().endsWith(".bat")) {
            // Windows script execution
            processBuilder = new ProcessBuilder("cmd.exe", "/c", scriptFile.getAbsolutePath());
        } else {
            // Unix-based script execution
            processBuilder = new ProcessBuilder(scriptFile.getAbsolutePath());
        }

        try {
            Process process = processBuilder.start();
            process.waitFor();
            System.out.println("Script executed successfully.");
        } catch (IOException e) {
            System.out.println("Error executing the script: " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("Script execution interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
