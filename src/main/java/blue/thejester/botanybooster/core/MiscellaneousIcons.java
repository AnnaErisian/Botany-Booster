/**
 * Adapted from Botania.  Original licence below.
 * Most of the code here has been removed - I'm only registering a few textures
 */

/**
 * This class was created by <williewillus>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package blue.thejester.botanybooster.core;

import blue.thejester.botanybooster.BotanyBooster;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = BotanyBooster.MODID)
public class MiscellaneousIcons {

	public static final MiscellaneousIcons INSTANCE = new MiscellaneousIcons();

	public TextureAtlasSprite
			longCorporeaWorldIcon;

	@SubscribeEvent
	public void onTextureStitch(TextureStitchEvent.Pre evt) {
		longCorporeaWorldIcon = evt.getMap().registerSprite(new ResourceLocation(BotanyBooster.MODID + ":items" + "/" + "spark_long_corporea"));
	}

	private MiscellaneousIcons() {}
}
