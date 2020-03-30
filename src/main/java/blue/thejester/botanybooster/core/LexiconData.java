package blue.thejester.botanybooster.core;

import blue.thejester.botanybooster.block.BlockArrayedCrystalCube;
import blue.thejester.botanybooster.block.BlockSecondSunCore;
import blue.thejester.botanybooster.block.Blocks;
import blue.thejester.botanybooster.block.subtile.functional.SubTileDryacinth;
import blue.thejester.botanybooster.block.subtile.functional.SubTileOrechidCunctus;
import blue.thejester.botanybooster.item.ItemFlyLens;
import blue.thejester.botanybooster.item.ItemLaunchLens;
import blue.thejester.botanybooster.item.ItemLongCorporeaSpark;
import blue.thejester.botanybooster.item.Items;
import blue.thejester.botanybooster.item.bauble.*;
import blue.thejester.botanybooster.recipe.ModPetalRecipe;
import net.minecraft.item.Item;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.common.lexicon.AlfheimLexiconEntry;
import vazkii.botania.common.lexicon.BasicLexiconEntry;
import vazkii.botania.common.lexicon.page.PageCraftingRecipe;
import vazkii.botania.common.lexicon.page.PageText;

public class LexiconData {

    public static LexiconEntry dryacinth;
    public static LexiconEntry orechidCunctus;
    public static LexiconEntry clearCrystalCube;
    public static LexiconEntry secondSun;
    public static LexiconEntry longRangeSpark;
    public static LexiconEntry launchLens;
    public static LexiconEntry flyLens;

    public static LexiconEntry crownOfAtlantis;
    public static LexiconEntry eyeOfHathor;
    public static LexiconEntry consumptionCharm;
    public static LexiconEntry ballosCrown;
    public static LexiconEntry fairySignet;
    public static LexiconEntry leapBelt;
    public static LexiconEntry argentumShoulderpad;
    public static LexiconEntry basiliskHarness;
    public static LexiconEntry livingOil;
    public static LexiconEntry coatOfArms;
    public static LexiconEntry stabilizingSash;
    public static LexiconEntry tinyWizardHat;

    public static void init() {
        dryacinth = new BasicLexiconEntry(SubTileDryacinth.NAME, BotaniaAPI.categoryFunctionalFlowers);
        dryacinth.setLexiconPages(
                BotaniaAPI.internalHandler.textPage("0"),
                BotaniaAPI.internalHandler.petalRecipePage("1", ModPetalRecipe.dryacinthRecipe)
        );

        orechidCunctus = new AlfheimLexiconEntry(SubTileOrechidCunctus.NAME, BotaniaAPI.categoryFunctionalFlowers);
        orechidCunctus.setLexiconPages(
                BotaniaAPI.internalHandler.textPage("0"),
                BotaniaAPI.internalHandler.petalRecipePage("1", ModPetalRecipe.orechidCunctusRecipe)
        );

        clearCrystalCube = new BasicLexiconEntry(BlockArrayedCrystalCube.NAME, BotaniaAPI.categoryEnder);
        clearCrystalCube.setLexiconPages(
                new PageText("0"),
                new PageCraftingRecipe("1", Blocks.corporeaArrayedCrystalCube.getRegistryName())
        );

        secondSun = new AlfheimLexiconEntry(BlockSecondSunCore.NAME, BotaniaAPI.categoryDevices);
        secondSun.setLexiconPages(
                new PageText("0"),
                new PageCraftingRecipe("1", Blocks.secondSunCore0.getRegistryName()),
                new PageCraftingRecipe("2", Items.ssUpgrade0.getRegistryName()),
                new PageCraftingRecipe("3", Items.ssUpgrade1.getRegistryName()),
                new PageCraftingRecipe("4", Items.ssUpgrade2.getRegistryName()),
                new PageCraftingRecipe("5", Items.ssUpgrade3.getRegistryName()),
                new PageCraftingRecipe("6", Items.ssUpgrade4.getRegistryName()),
                new PageCraftingRecipe("7", Items.ssUpgrade5.getRegistryName())
        );

        longRangeSpark = new BasicLexiconEntry(ItemLongCorporeaSpark.NAME, BotaniaAPI.categoryEnder);
        longRangeSpark.setLexiconPages(
                new PageText("0"),
                new PageCraftingRecipe("1", Items.longRangeCorporeaSpark.getRegistryName())
        );

        launchLens = new BasicLexiconEntry(ItemLaunchLens.NAME, BotaniaAPI.categoryMana);
        launchLens.setLexiconPages(
                new PageText("0"),
                new PageCraftingRecipe("1", Items.launchLens.getRegistryName())
        );

        flyLens = new BasicLexiconEntry(ItemFlyLens.NAME, BotaniaAPI.categoryMana);
        flyLens.setLexiconPages(
                new PageText("0"),
                new PageCraftingRecipe("1", Items.flyLens.getRegistryName())
        );

        crownOfAtlantis = new BasicLexiconEntry(ItemCrownOfAtlantis.NAME, BotaniaAPI.categoryBaubles);
        crownOfAtlantis.setLexiconPages(
                new PageText("0"),
                new PageCraftingRecipe("1", Items.crownOfAtlantis.getRegistryName())
        );
        eyeOfHathor = new BasicLexiconEntry(ItemBreederEye.NAME, BotaniaAPI.categoryBaubles);
        eyeOfHathor.setLexiconPages(
                new PageText("0"),
                new PageCraftingRecipe("1", Items.eyeOfHathor.getRegistryName())
        );
        consumptionCharm = new BasicLexiconEntry(ItemFastEatCharm.NAME, BotaniaAPI.categoryBaubles);
        consumptionCharm.setLexiconPages(
                new PageText("0"),
                new PageCraftingRecipe("1", Items.consumptionCharm.getRegistryName())
        );
        ballosCrown = new BasicLexiconEntry(ItemBallosCrown.NAME, BotaniaAPI.categoryBaubles);
        ballosCrown.setLexiconPages(
                new PageText("0"),
                new PageCraftingRecipe("1", Items.ballosCrown.getRegistryName())
        );
        fairySignet = new BasicLexiconEntry(ItemFairySignet.NAME, BotaniaAPI.categoryBaubles);
        fairySignet.setLexiconPages(
                new PageText("0"),
                new PageCraftingRecipe("1", Items.fairySignet.getRegistryName())
        );
        leapBelt = new BasicLexiconEntry(ItemSuperHopBelt.NAME, BotaniaAPI.categoryBaubles);
        leapBelt.setLexiconPages(
                new PageText("0"),
                new PageCraftingRecipe("1", Items.leapBelt.getRegistryName())
        );
        argentumShoulderpad = new BasicLexiconEntry(ItemArgentumArmor.NAME, BotaniaAPI.categoryBaubles);
        argentumShoulderpad.setLexiconPages(
                new PageText("0"),
                new PageCraftingRecipe("1", Items.argentumShoulderpad.getRegistryName())
        );
        basiliskHarness = new BasicLexiconEntry(ItemBasiliskHarness.NAME, BotaniaAPI.categoryBaubles);
        basiliskHarness.setLexiconPages(
                new PageText("0"),
                new PageCraftingRecipe("1", Items.basiliskHarness.getRegistryName())
        );
        livingOil = new BasicLexiconEntry(ItemLivingOil.NAME, BotaniaAPI.categoryBaubles);
        livingOil.setLexiconPages(
                new PageText("0"),
                new PageCraftingRecipe("1", Items.livingOil.getRegistryName())
        );
        coatOfArms = new BasicLexiconEntry(ItemCoatOfArms.NAME, BotaniaAPI.categoryBaubles);
        coatOfArms.setLexiconPages(
                new PageText("0"),
                new PageCraftingRecipe("1", Items.coatOfArms.getRegistryName())
        );
        stabilizingSash = new BasicLexiconEntry(ItemStabilizingSash.NAME, BotaniaAPI.categoryBaubles);
        stabilizingSash.setLexiconPages(
                new PageText("0"),
                new PageCraftingRecipe("1", Items.stabilizingSash.getRegistryName())
        );
        if(Items.tinyWizardHat != null) {
            tinyWizardHat = new BasicLexiconEntry(ItemTinyWizardHat.NAME, BotaniaAPI.categoryBaubles);
            tinyWizardHat.setLexiconPages(
                    new PageText("0"),
                    new PageCraftingRecipe("1", Items.tinyWizardHat.getRegistryName())
            );
        }
    }

}
