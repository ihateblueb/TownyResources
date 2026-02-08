package io.github.townyadvanced.townyresources.listeners;

import com.palmergames.bukkit.towny.event.statusscreen.NationStatusScreenEvent;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Translator;
import com.palmergames.bukkit.towny.utils.TownyComponents;
import io.github.townyadvanced.townyresources.metadata.TownyResourcesGovernmentMetaDataController;
import io.github.townyadvanced.townyresources.settings.TownyResourcesSettings;
import io.github.townyadvanced.townyresources.util.TownyResourcesMessagingUtil;
import net.kyori.adventure.text.Component;

import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * 
 * @author Goosius
 *
 */
public class TownyResourcesNationEventListener implements Listener {

	/*
	 * TownyResources will add resource info to the nation screen
	 */
	@EventHandler
	public void onNationStatusScreen(NationStatusScreenEvent event) {
		if (TownyResourcesSettings.isEnabled()) {
			Translator translator = Translator.locale(event.getCommandSender());
			Nation nation = event.getNation();
			String productionAsString = TownyResourcesGovernmentMetaDataController.getDailyProduction(nation);
			String availableAsString = TownyResourcesGovernmentMetaDataController.getAvailableForCollection(nation);

			if(productionAsString.isEmpty() && availableAsString.isEmpty())
				return;

			TextComponent.Builder builder = Component.text();
			builder.append(Component.text("[", NamedTextColor.GRAY));
			builder.append(Component.text("Resources", NamedTextColor.GREEN));
			builder.append(Component.text("]", NamedTextColor.GRAY));

			TextComponent.Builder hoverBuilder = Component.text();
			hoverBuilder.append(TownyComponents.legacy(translator.of("townyresources.nation.screen.header"))).appendNewline();
			hoverBuilder.append(TownyResourcesMessagingUtil.getSubComponentForGovernmentScreens(translator, productionAsString, "townyresources.nation.screen.daily.production")).appendNewline();
			hoverBuilder.append(TownyResourcesMessagingUtil.getSubComponentForGovernmentScreens(translator, availableAsString, "townyresources.nation.screen.available.for.collection"));

			builder.hoverEvent(hoverBuilder.build());

			event.getStatusScreen().addComponentOf("TownyResources", builder.build());
		}
	}
}
