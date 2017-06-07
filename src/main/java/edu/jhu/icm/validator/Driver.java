package edu.jhu.icm.validator;


import org.apache.commons.cli.*;
import edu.jhu.icm.validator.ApplicationConfigs;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

/**
 * Application to upload CSV files into OpenTSDB.
 * Created by rliu14 on 7/8/16.
 */
public class Driver {

	static Options options = new Options();

	/**
	 * Registers command line interface options.
	 */
	private static void registerOptions() {
		Option version = new Option("v", "version", false, "Displays version information");

		Option filename = new Option("f", "filename", true, "File to read");
		Option directory = new Option("d", "directory", false, "Directory to Process");
		Option results = new Option("r", "results", false, "Process Results");

		options.addOption(version);
		options.addOption(filename);
		options.addOption(directory);
		options.addOption(results);
		
	}

	private static void printHelp() {
		HelpFormatter helpFormatter = new HelpFormatter();
		helpFormatter.printHelp("java -jar validator.jar", options);
	}

	public static void main(String[] args) {

		registerOptions();

		CommandLineParser parser = new BasicParser();

		try {
			CommandLine cmd = parser.parse(options, args);
			ApplicationContext context =  new AnnotationConfigApplicationContext(ApplicationConfigs.class);

			if (cmd.hasOption("version")) {
				String versionInfo = (String)context.getBean("version");
				System.out.println(versionInfo);

			} else if (cmd.hasOption("results")) {
				
				String filename = cmd.getOptionValue("filename");
				boolean directory = false;
				if (cmd.hasOption("directory")) directory = true;

				validatorFacade facade = (validatorFacade)context.getBean("validatorFacade");
				facade.validateTrew(filename, directory, true);

			} else if (cmd.hasOption("filename")) {
				String filename = cmd.getOptionValue("filename");
				boolean directory = false;
				if (cmd.hasOption("directory")) directory = true;

				validatorFacade facade = (validatorFacade)context.getBean("validatorFacade");
				facade.validateTrew(filename, directory);

			} else {
				printHelp();
			}

		} catch (ParseException | IOException | java.text.ParseException e) {
			System.err.println(e.getMessage());
		}

	}
}
