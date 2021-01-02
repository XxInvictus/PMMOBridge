package com.minttea.pmmobridge.events;

import com.hollingsworth.arsnouveau.api.event.ManaRegenCalcEvent;
import com.hollingsworth.arsnouveau.api.event.MaxManaCalcEvent;
import com.hollingsworth.arsnouveau.api.event.SpellCastEvent;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import harmonised.pmmo.skills.Skill;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

//@Mod.EventBusSubscriber(modid = ArsNouveau.MODID)
public class ArsCompatEventHandler {

    public static final Logger LOGGER = LogManager.getLogger();


    @SubscribeEvent
    public static void awardSpellCastXp(SpellCastEvent event)
    {
        LivingEntity entity = event.getEntityLiving();
        List<AbstractSpellPart> spell = event.spell;
        int xpAward = 1;
        LOGGER.debug("Spell cast!");
        for (AbstractSpellPart spellpart: spell
             ) {
            LOGGER.debug("Adding xp for "+ spellpart.name);
            switch(spellpart.getTier()){
                case THREE: xpAward = 26;Skill.MAGIC.addXp(entity.getUniqueID(), xpAward , null, true, false);break;
                case TWO: xpAward = 12;Skill.MAGIC.addXp(entity.getUniqueID(), xpAward , null, true, false);break;
                case ONE: xpAward = 5;Skill.MAGIC.addXp(entity.getUniqueID(), xpAward , null, true, false);break;
                default:

            }


        }

    }
    @SubscribeEvent
    public static void maxManaByLevel(MaxManaCalcEvent event)
    {
        int magicLevel = Skill.MAGIC.getLevel(event.getEntity().getUniqueID());
        int maxMana = event.getMax();
        float manaBonus = 1+ (float)magicLevel/20;
        LOGGER.debug("Changing mana from " + maxMana + " by " + manaBonus);
        event.setMax((int)(maxMana * manaBonus));
    }
    @SubscribeEvent
    public static void manaRegenByLevel(ManaRegenCalcEvent event)
    {
        int magicLevel = Skill.MAGIC.getLevel(event.getEntity().getUniqueID());
        int regen = (int) event.getRegen();
        float manaBonus = 1+(float) magicLevel/20;
        event.setRegen((int)(regen * manaBonus));
    }

}
