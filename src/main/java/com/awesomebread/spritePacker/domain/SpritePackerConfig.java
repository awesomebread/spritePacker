package com.awesomebread.spritePacker.domain;

import java.io.File;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpritePackerConfig {

	@NotNull
	private Integer columns;
	
	private Integer rows;
	
	@NotNull
	private File input;
	
	private File output;
	
	private Integer padding;
	
	private Integer spriteWidth;
	
	private Integer spriteHeight;
	
}
