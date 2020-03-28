package blue.thejester.botanybooster.potion;

import blue.thejester.botanybooster.BotanyBooster;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = BotanyBooster.MODID)
public class ModPotions {

	public static final Potion stabilized = new PotionStabilized(false, 0xffffff);

	@SubscribeEvent
	public static void registerPotions(RegistryEvent.Register<Potion> evt)
	{
		evt.getRegistry().register(stabilized);
	}
}
