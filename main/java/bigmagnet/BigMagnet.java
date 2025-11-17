package bigmagnet;


import bigmagnet.buffs.MagnetBuff;
import bigmagnet.items.*;
import bigmagnet.items.potions.GreaterMagnetPotionItem;
import bigmagnet.items.potions.InfiniteGreaterMagnetPotionItem;
import bigmagnet.items.potions.InfiniteMagnetPotionItem;
import bigmagnet.items.potions.MagnetPotionItem;
import bigmagnet.magnetchest.MagnetChest;
import customsettingslib.components.settings.IntSetting;
import customsettingslib.settings.CustomModSettings;
import customsettingslib.settings.CustomModSettingsGetter;
import necesse.engine.modLoader.ModLoader;
import necesse.engine.modLoader.ModSettings;
import necesse.engine.modLoader.annotations.ModEntry;
import necesse.engine.modifiers.ModifierValue;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.ItemRegistry;
import necesse.engine.registries.ObjectRegistry;
import necesse.engine.registries.RecipeTechRegistry;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.SimplePotionBuff;
import necesse.gfx.gameFont.FontOptions;
import necesse.inventory.item.Item;
import necesse.inventory.item.trinketItem.CombinedTrinketItem;
import necesse.inventory.recipe.Ingredient;
import necesse.inventory.recipe.Recipe;
import necesse.inventory.recipe.Recipes;

import java.awt.*;


@ModEntry
public class BigMagnet {
    public static CustomModSettingsGetter settingsGetter;
    public void init() {
        System.out.println("Big Magnet loaded!");

        ItemRegistry.registerItem("magnetpotion", new MagnetPotionItem(), 10.0F, true);
        ItemRegistry.registerItem("greatermagnetpotion", new GreaterMagnetPotionItem(), 30.0F, true);
        BuffRegistry.registerBuff("magnetpotion", new SimplePotionBuff(new ModifierValue[]{new ModifierValue(BuffModifiers.ITEM_PICKUP_RANGE, 2.0F)}));
        BuffRegistry.registerBuff("greatermagnetpotion", new SimplePotionBuff(new ModifierValue[]{new ModifierValue(BuffModifiers.ITEM_PICKUP_RANGE, 4.0F)}));

        ItemRegistry.registerItem("supermagnet", new SuperMagnet(), 200.0F, true);
        BuffRegistry.registerBuff("supermagnettrinket", new MagnetBuff());

        ItemRegistry.registerItem("magnetbelt", new CombinedTrinketItem(Item.Rarity.RARE, 800, new String[]{"itemattractor","shinebelt"}).addDisables(new String[]{"itemattractor","toolbox","supermagnet","shinebelt"}), 200.0F, true);

        if(ModLoader.getEnabledMods().stream().anyMatch(loadedMod -> loadedMod.id.equalsIgnoreCase("oblio.endlesspotions"))){
            ItemRegistry.registerItem("infinitemagnetpotion", new InfiniteMagnetPotionItem(), 10.0F, true);
            ItemRegistry.registerItem("infinitegreatermagnetpotion", new InfiniteGreaterMagnetPotionItem(), 30.0F, true);
        }

        ObjectRegistry.registerObject("magnetchest",new MagnetChest(),10F,true);
    }

    public void postInit() {
        Recipes.registerModRecipe(new Recipe(
                "magnetpotion",
                1,
                RecipeTechRegistry.VOID_ALCHEMY,
                new Ingredient[]{
                        new Ingredient("copperbar", 2),
                        new Ingredient("voidshard", 2),
                        new Ingredient("glassbottle", 1)
                }
        ).showAfter("spelunkerpotion"));
        Recipes.registerModRecipe(new Recipe(
                "greatermagnetpotion",
                1,
                RecipeTechRegistry.FALLEN_ALCHEMY,
                new Ingredient[]{
                        new Ingredient("magnetpotion", 1),
                        new Ingredient("alchemyshard", 10)
                }
        ).showAfter("greaterbuildingpotion"));
        Recipes.registerModRecipe(new Recipe(
                "magnetbelt",
                1,
                RecipeTechRegistry.TUNGSTEN_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("itemattractor", 1),
                        new Ingredient("shinebelt", 1)
                }
        ).showAfter("toolbox"));
        Recipes.registerModRecipe(new Recipe(
                "supermagnet",
                1,
                RecipeTechRegistry.TUNGSTEN_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("itemattractor", 1),
                        new Ingredient("piratetelescope", 1)
                }
        ).showAfter("magnetbelt"));

        Recipes.registerModRecipe(new Recipe(
                "magnetchest",
                1,
                RecipeTechRegistry.TUNGSTEN_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("itemattractor", 1),
                        new Ingredient("storagebox", 1)
                }
        ).showAfter("storagebox"));

        if (ModLoader.getEnabledMods().stream().anyMatch(loadedMod -> loadedMod.id.equalsIgnoreCase("oblio.endlesspotions"))) {
            Recipes.registerModRecipe(new Recipe(
                    "infinitemagnetpotion",
                    1,
                    RecipeTechRegistry.VOID_ALCHEMY,
                    new Ingredient[]{
                            new Ingredient("magnetpotion", 15)
                    }
            ).showAfter("magnetpotion"));
            Recipes.registerModRecipe(new Recipe(
                    "infinitegreatermagnetpotion",
                    1,
                    RecipeTechRegistry.FALLEN_ALCHEMY,
                    new Ingredient[]{
                            new Ingredient("greatermagnetpotion", 15)
                    }
            ).showAfter("greatermagnetpotion"));
        }
    }
    public ModSettings initSettings() {
        CustomModSettings customModSettings = new CustomModSettings()
                .addIntSetting("magnetRadius", 8, 0, 50, IntSetting.DisplayMode.INPUT_BAR)
                .addParagraph("magnetsizetext",new FontOptions(16).color(Color.RED).outline(true).shadow(Color.BLACK,1,1),-1);
        settingsGetter = customModSettings.getGetter();
        return customModSettings;
    }
}
