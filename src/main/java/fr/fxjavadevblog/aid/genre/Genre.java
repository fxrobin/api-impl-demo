package fr.fxjavadevblog.aid.genre;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Enumeration of all genre of Video Games for Atari ST.
 * 
 * @author François-Xavier Robin
 *
 */

@Schema(enumeration = { "arcade", "education", "fighting", "pinball", "plateform", "reflexion", "rpg", "shoot-them-up", "simulation", "sport" })
public enum Genre {
	@JsonProperty(value = "arcade")
	ARCADE,

	@JsonProperty(value = "education")
	EDUCATION,

	@JsonProperty(value = "fighting")
	FIGHTING,

	@JsonProperty(value = "pinball")
	PINBALL,

	@JsonProperty(value = "platform")
	PLATFORM,

	@JsonProperty(value = "reflexion")
	REFLEXION,

	@JsonProperty(value = "rpg")
	RPG,

	@JsonProperty(value = "shoot-them-up")
	SHOOT_THEM_UP,

	@JsonProperty(value = "simulation")
	SIMULATION,

	@JsonProperty(value = "sport")
	SPORT;
}
