package blue.thejester.botanybooster;

import blue.thejester.botanybooster.core.CommonProxy;
import blue.thejester.botanybooster.core.handler.BBEventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

import java.util.LinkedHashSet;
import java.util.Set;

@Mod(modid = BotanyBooster.MODID, name = BotanyBooster.NAME, version = BotanyBooster.VERSION, dependencies = BotanyBooster.DEPENDS)
public class BotanyBooster
{
    public static final String MODID = "botanybooster";
    public static final String NAME = "Botany Booster";
    public static final String VERSION = "1.0";
    public static final String DEPENDS = "required-after:botania;";

    public static Logger logger;

    public static Set<String> subtilesForCreativeMenu = new LinkedHashSet();

    // The instance of your mod that Forge uses.  Optional.
    @Mod.Instance(BotanyBooster.MODID)
    public static BotanyBooster instance;

    // Says where the client and server 'proxy' code is loaded.
    @SidedProxy(clientSide="blue.thejester.botanybooster.core.ClientOnlyProxy", serverSide="blue.thejester.botanybooster.core.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();

        proxy.preInit();

        BBEventHandler eventHandler = new BBEventHandler();
        MinecraftForge.EVENT_BUS.register(eventHandler);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit();
    }
}
