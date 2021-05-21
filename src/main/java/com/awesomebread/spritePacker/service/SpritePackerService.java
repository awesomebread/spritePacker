package com.awesomebread.spritePacker.service;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.validation.Valid;

import com.awesomebread.spritePacker.domain.SpritePackerConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SpritePackerService {

	public void packSprites(@Valid SpritePackerConfig config) throws IOException {
		log.info("Grabbing Sprites");
		
		List<String> spritesToPack = Arrays.asList(config.getInput().list()).stream()
				.filter(sprite -> sprite.substring(sprite.length()-3).equals("png"))
				.collect(Collectors.toList());
		
		log.info("Found " + spritesToPack.size() + " sprites to pack.");
		
		if (config.getSpriteWidth() == null) {
			BufferedImage image = ImageIO.read(new File(config.getInput().getAbsolutePath() + "\\" + spritesToPack.get(0)));
			config.setSpriteWidth(image.getWidth());
		}
		
		if (config.getSpriteHeight() == null) {
			BufferedImage image = ImageIO.read(new File(config.getInput().getAbsolutePath() + "\\" + spritesToPack.get(0)));
			config.setSpriteHeight(image.getHeight());
		}
		
		if (config.getRows() == null) {
			config.setRows((int) Math.ceil((double) spritesToPack.size()/ (double) config.getColumns()));
		}
		
		log.info("Starting packing of {} {}x{} sprites with a padding of {}px.  Will be fit to {} columns wide and {} rows tall.", spritesToPack.size(), config.getSpriteWidth(), config.getSpriteHeight(), config.getPadding(), config.getColumns(), config.getRows());
		
		int spriteSheetWidth = (config.getColumns() * config.getSpriteWidth()) + (config.getColumns() * config.getPadding());
		int spriteSheetHeight = (config.getRows() * config.getSpriteHeight()) + (config.getRows() * config.getPadding());
		
		BufferedImage spriteSheet = new BufferedImage(spriteSheetWidth, spriteSheetHeight, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics graphics = spriteSheet.getGraphics();
		
		int currentXLoc = 0;
		int currentYLoc = 0;
		
		for (String spriteName : spritesToPack) {
			BufferedImage sprite = ImageIO.read(new File(config.getInput().getAbsolutePath() + "\\" + spriteName));
			
			graphics.drawImage(sprite, currentXLoc, currentYLoc, null);
			
			currentXLoc += config.getSpriteWidth() + config.getPadding();
			
			if (currentXLoc >= spriteSheetWidth) {
				currentXLoc = 0;
				currentYLoc += config.getSpriteHeight() + config.getPadding();
			}	
		}
		
		ImageIO.write(spriteSheet, "png", config.getOutput());
		
		log.info("Finished packing.  Result is {}x{} and can be found here: {}", spriteSheetWidth, spriteSheetHeight, config.getOutput().getAbsolutePath());
	}
}
