package fredver.clientserver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * The main class of the application, that installs it in the preferred directory
 */
public class Installer {

	/**
	 * Generates a self destructing OS-specific installation script and executes it
	 * @param args Unused
	 */
	public static void main(String[] args) {
		
        String os = System.getProperty("os.name").toLowerCase();
        boolean isWindows = os.contains("win");
        boolean isMac = os.contains("mac");
        boolean isLinux = os.contains("nix") || os.contains("nux");

        if (!isWindows && !isMac && !isLinux) {
            System.out.println("Detected operating system not Windows, MacOS, or linux");
            System.out.println("only these operating systems are supported");
            System.out.println("exiting program...");
            return;
        }
        
        File installDir = askInstallDir();
        
        int installationType = askInstallationType();

        try {
        	File scriptFile = null;
            if (isWindows) {
                scriptFile = generateWindowsScript(installDir, installationType);
            } else if (isMac || isLinux) {
                scriptFile = generateUnixScript(installDir, installationType);
            }
            executeScript(scriptFile);
            System.out.println("Installation completed successfully");
            System.out.println("Closing installation program...");
            return;
        } catch (IOException e) {
        	System.out.println("For the installation a temporary script is created and executed");
            System.out.println("There was an error writing or executing the build script: " + e.getMessage());
        }
    }
	
	/**
     * Used internally to ask the installation directory inside the main function
     * @return The File object representing the directory
     */
    private static File askInstallDir() {
    	@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
    	
    	while(true) {
        	System.out.println("Enter the installation directory where client/server JARs will be saved:");
            String installDirPath = scanner.nextLine();
            File installDir = new File(installDirPath);
            
            if (!installDir.exists()) {
        		if (!installDir.mkdirs()) {
        			System.out.println("Failed to create installation directory");
        			System.out.println("Path only created partially");
        			System.out.println("Check permissions and try again");
        			continue;
                }
            } else if (!installDir.isDirectory()) {
                System.out.println("The specified path is not a directory");
                System.out.println("Path created but not target folder");
                System.out.println("Try again");
                continue;
            }
            return installDir;
        }
    }

    /**
     * Used internally to ask the type of installation (client, server, or both) inside the main function
     * @return the code for installation. 1:client<br>2:server<br>3:both
     */
    private static int askInstallationType() {
    	
    	@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
    	int choice = 0;
        while (true) {
            System.out.println("Select installation option:");
            System.out.println("1: client");
            System.out.println("2: server");
            System.out.println("3: both");

            try {
                choice = Integer.parseInt(scanner.nextLine());
                if ( (choice == 1) || (choice == 2) || (choice == 3) ) {
                    return choice;
                } else {
                    System.out.println("Invalid input");
                    System.out.println("Please try again");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Not a number");
                continue;
            }
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
    	        .append("set REPO_URL=https://github.com/fredneedsausername/ServerPhone.git\n")
    	        .append("echo Cloning the repository...\n")
    	        .append("git clone %REPO_URL%\n")
    	        .append("cd ServerPhone || exit /b\n")
    	        .append("echo Running Maven build...\n")
    	        .append("mvn clean package\n")
    	        .append("echo Build completed.\n");

    	// Copy executables to installDir
    	if (choice == 1 || choice == 3) {
    	    scriptContent.append("copy target\\client.jar ").append(installDir.getAbsolutePath()).append("\\client.jar\n");
    	}
    	if (choice == 2 || choice == 3) {
    	    scriptContent.append("copy target\\server.jar ").append(installDir.getAbsolutePath()).append("\\server.jar\n");
    	}

    	// Add commands to delete the source code and repository files
    	scriptContent.append("echo Removing source code and repository files...\n")
    	        .append("cd ..\n") // Move back to the parent directory
    	        .append("rmdir /s /q ServerPhone\n"); // Remove the cloned repository folder

    	// Add self-delete commands
    	scriptContent.append("(echo @echo off\n")
    	        .append("echo del \"%~f0\") > \"%temp%\\delete_self.bat\"\n")
    	        .append("start \"\" /b \"%temp%\\delete_self.bat\"\n");

    	// Write the script file
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
    	        .append("REPO_URL=https://github.com/fredneedsausername/ServerPhone.git\n")
    	        .append("echo \"Cloning the repository...\"\n")
    	        .append("git clone \"$REPO_URL\"\n")
    	        .append("cd ServerPhone || exit\n")
    	        .append("echo \"Running Maven build...\"\n")
    	        .append("mvn clean package\n")
    	        .append("echo \"Build completed.\"\n");

    	// Copy executables to installDir
    	if (choice == 1 || choice == 3) {
    	    scriptContent.append("cp target/client.jar ").append(installDir.getAbsolutePath()).append("/client.jar\n");
    	}
    	if (choice == 2 || choice == 3) {
    	    scriptContent.append("cp target/server.jar ").append(installDir.getAbsolutePath()).append("/server.jar\n");
    	}

    	// Add commands to delete source code and repository files
    	scriptContent.append("echo \"Removing source code and repository files...\"\n")
    	        .append("cd ..\n") // Go back to the parent directory
    	        .append("rm -rf ServerPhone\n"); // Remove the cloned repository

    	// Add self-delete command
    	scriptContent.append("rm -- \"$0\"\n");

    	// Write the script file
    	File scriptFile = new File(installDir, "build.sh");
    	writeFile(scriptFile, scriptContent.toString());
    	scriptFile.setExecutable(true); // Make executable
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
