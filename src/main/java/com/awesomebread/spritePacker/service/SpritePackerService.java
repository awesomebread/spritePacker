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
		
		BufferedImage image = ImageIO.read(new File(config.getInput().getAbsolutePath() + "\\" + spritesToPack.get(0)));
		
		if (config.getSpriteWidth() == null) {
			config.setSpriteWidth(image.getWidth());
		}
		
		if (config.getSpriteHeight() == null) {
			config.setSpriteHeight(image.getHeight());
		}
		
		Integer cropStartX = null;
		Integer cropStartY = null;
		Integer cropEndX = null;
		Integer cropEndY = null;
		
		if (config.isCrop()) {
			for (int i = 0; i < image.getWidth(); i++) {
				for (int j = 0; j < image.getHeight(); j++) {
					if ((image.getRGB(i, j)>>24) != 0x00) {
						if (cropStartY == null || j < cropStartY) {
							cropStartY = j;
						}
						if (cropEndY == null || j > cropEndY) {
							cropEndY = j;
						}
						if (cropStartX == null || i < cropStartX) {
							cropStartX = i;
						}
						if (cropEndX == null || i > cropEndX) {
							cropEndX = i;
						}
					}
				}
			}
			
			if (config.getCropStartX() != null) {
				cropStartX = config.getCropStartX();
			}
			
			if (config.getCropStartY() != null) {
				cropStartY = config.getCropStartY();
			}
			
			if (config.getCropEndX() != null) {
				cropEndX = config.getCropEndX();
			}
			
			if (config.getCropEndY() != null) {
				cropEndY = config.getCropEndY();
			}
			
			int cropWidth = cropEndX - cropStartX;
			int cropHeight = cropEndY - cropStartY;
			
			log.info("Images set to be croped from {}x{} to {}x{} starting at ({}, {}) and ending at ({},{}).",  config.getSpriteWidth(), config.getSpriteHeight(), cropWidth, cropHeight, cropStartX, cropStartY, cropEndX, cropEndY);
			
			config.setSpriteWidth(cropWidth);
			config.setSpriteHeight(cropHeight);
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
			
			if (config.isCrop()) {
				sprite = sprite.getSubimage(cropStartX, cropStartY, config.getSpriteWidth(), config.getSpriteHeight());
			}
			
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
