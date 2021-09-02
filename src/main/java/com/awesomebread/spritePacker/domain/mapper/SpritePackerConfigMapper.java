package com.awesomebread.spritePacker.domain.mapper;

import java.io.File;

import org.apache.commons.cli.CommandLine;

import com.awesomebread.spritePacker.domain.SpritePackerConfig;
import com.awesomebread.spritePacker.domain.SpritePackerConfig.SpritePackerConfigBuilder;

public class SpritePackerConfigMapper {

	public static SpritePackerConfig mapCommandLineArgsToConfig(CommandLine cmd) {
		SpritePackerConfigBuilder configBuilder = SpritePackerConfig.builder();
		
		if (cmd.hasOption("c") || cmd.hasOption("columns")) {
			configBuilder.columns(Integer.parseInt(cmd.getOptionValue("c")));
		}
		
		if (cmd.hasOption("r") || cmd.hasOption("rows")) {
			configBuilder.rows(Integer.parseInt(cmd.getOptionValue("r")));
		}
		
		if (cmd.hasOption("i") || cmd.hasOption("input")) {
			configBuilder.input(new File(cmd.getOptionValue("i")));
		}
		
		if (cmd.hasOption("o") || cmd.hasOption("output")) {
			configBuilder.output(new File(cmd.getOptionValue("o")));
		} else {
			configBuilder.output(new File(cmd.getOptionValue("i") + "\\spriteSheet.png"));
		}
		
		if (cmd.hasOption("p") || cmd.hasOption("padding")) {
			configBuilder.padding(Integer.parseInt(cmd.getOptionValue("p")));
		} else {
			configBuilder.padding(0);
		}
		
		if (cmd.hasOption("x") || cmd.hasOption("width")) {
			configBuilder.spriteWidth(Integer.parseInt(cmd.getOptionValue("x")));
		}
		
		if (cmd.hasOption("y") || cmd.hasOption("height")) {
			configBuilder.spriteHeight(Integer.parseInt(cmd.getOptionValue("y")));
		}
		
		configBuilder.crop(cmd.hasOption("cr") || cmd.hasOption("crop"));
		
		if (cmd.hasOption("csx") || cmd.hasOption("cropStartX")) {
			configBuilder.cropStartX(Integer.parseInt(cmd.getOptionValue("csx")));
		}
		
		if (cmd.hasOption("csy") || cmd.hasOption("cropStartY")) {
			configBuilder.cropStartY(Integer.parseInt(cmd.getOptionValue("csy")));
		}
		
		if (cmd.hasOption("cex") || cmd.hasOption("cropEndX")) {
			configBuilder.cropEndX(Integer.parseInt(cmd.getOptionValue("cex")));
		}
		
		if (cmd.hasOption("cey") || cmd.hasOption("cropEndY")) {
			configBuilder.cropEndY(Integer.parseInt(cmd.getOptionValue("cey")));
		}
		
		return configBuilder.build();
	}
	
}
