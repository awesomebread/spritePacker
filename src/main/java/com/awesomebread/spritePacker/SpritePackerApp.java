package com.awesomebread.spritePacker;

import java.util.Arrays;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.awesomebread.spritePacker.domain.SpritePackerConfig;
import com.awesomebread.spritePacker.domain.mapper.SpritePackerConfigMapper;
import com.awesomebread.spritePacker.service.SpritePackerService;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class SpritePackerApp implements CommandLineRunner{

	private SpritePackerService spritePackerService;
	
	public SpritePackerApp() {
		spritePackerService = new SpritePackerService();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SpritePackerApp.class, args);
	}

	public void run(String... args) throws Exception {
		log.info("Starting packing with following args: " + Arrays.toString(args));
		
		CommandLine cmd = null;
		
		try {			
			Options options = getFullOptions();
			CommandLineParser clParser = new DefaultParser();
			
			CommandLine helpCmd = clParser.parse(getHelpOptions(), args, true);
			
			if (helpCmd.hasOption("h") || helpCmd.hasOption("help")) {
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp("This application will combine multiple png images into one png image.  This application assumes that all sprites are the same size, please specify the maximum sprite height and widths if you have different sizes.", options);
				System.exit(0);
			}
			
			cmd = clParser.parse(options, args);
			
		} catch (Exception e) {
			log.error("Exception parsing args: " + e.getMessage());
		}
		
		SpritePackerConfig config = SpritePackerConfigMapper.mapCommandLineArgsToConfig(cmd);
		spritePackerService.packSprites(config);
	}
	
	private Options getHelpOptions() {
		Options options = new Options();
		
		Option help = Option.builder("h")
				.longOpt("help")
				.desc("Prints this message")
				.build();
		
		options.addOption(help);
		return options;
	}
	
	private Options getFullOptions() {
		Options options = new Options();
		
		Option help = Option.builder("h")
				.longOpt("help")
				.desc("Prints this message")
				.build();
		
		Option col = Option.builder("c")
				.longOpt("columns")
				.hasArg()
				.required()
				.desc("The number of columns in the sprite sheet output.")
				.build();
		
		Option row = Option.builder("r")
				.longOpt("rows")
				.hasArg()
				.desc("The number of rows in the sprite sheet output.")
				.build();
		
		Option input = Option.builder("i")
				.longOpt("input")
				.hasArg()
				.required()
				.desc("Folder location of individual sprites.")
				.build();
		
		Option output = Option.builder("o")
				.longOpt("output")
				.hasArg()
				.desc("Filename and location for sprite sheet output.  Default is spriteSheet.png saved to the folder location containing the individual sprites.")
				.build();
		
		Option padding = Option.builder("p")
				.longOpt("padding")
				.hasArg()
				.desc("Padding between sprites in sprite sheet output. Default is 0px.")
				.build();
		
		Option spriteWidth = Option.builder("x")
				.longOpt("width")
				.hasArg()
				.desc("The maximum width of the individual sprites in pixels. Default is first selected sprites width.")
				.build();
		
		Option spriteHeight = Option.builder("y")
				.longOpt("height")
				.hasArg()
				.desc("The maximum height of the individual sprites in pixels. Default is first selected sprites height.")
				.build();
		Option crop = Option.builder("cr")
				.longOpt("crop")
				.desc("Will crop images to the smallest non transparent rectangle.  Based on the first image's bounds.")
				.build();
		
		Option cropStartX = Option.builder("csx")
				.longOpt("cropStartX")
				.hasArg()
				.desc("Upper left X coordinate for cropping. Use with -c option to override automatic cropping regions.")
				.build();
		
		Option cropStartY = Option.builder("csy")
				.longOpt("cropStartY")
				.hasArg()
				.desc("Upper left Y coordinate for cropping. Use with -c option to override automatic cropping regions.")
				.build();
		
		Option cropEndX = Option.builder("cex")
				.longOpt("cropEndX")
				.hasArg()
				.desc("Lower right X coordinate for cropping. Use with -c option to override automatic cropping regions.")
				.build();
		
		Option cropEndY = Option.builder("cey")
				.longOpt("cropEndY")
				.hasArg()
				.desc("Lower right Y coordinate for cropping. Use with -c option to override automatic cropping regions.")
				.build();
		
		options.addOption(help);
		options.addOption(col);
		options.addOption(row);
		options.addOption(input);
		options.addOption(output);
		options.addOption(padding);
		options.addOption(spriteWidth);
		options.addOption(spriteHeight);
		options.addOption(crop);
		options.addOption(cropStartX);
		options.addOption(cropStartY);
		options.addOption(cropEndX);
		options.addOption(cropEndY);
		
		return options;
	}
	
}
