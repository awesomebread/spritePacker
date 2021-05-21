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
		
		return configBuilder.build();
	}
	
}
