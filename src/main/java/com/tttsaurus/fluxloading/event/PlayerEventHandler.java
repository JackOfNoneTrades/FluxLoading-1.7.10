package com.tttsaurus.fluxloading.event;

import com.tttsaurus.fluxloading.FluxLoading;
import com.tttsaurus.fluxloading.util.ScreenshotHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.client.event.RenderHandEvent;

public class PlayerEventHandler {
    @SuppressWarnings("unused")
    @SubscribeEvent
    public void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent e) {

        FluxLoading.screenshotCache.clear();
        FluxLoading.logger.debug("Cleared screenshot cache");
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public void onPlayerLeaveFMLEvent(FMLNetworkEvent.ClientDisconnectionFromServerEvent e) {
        FluxLoading.screenshotCache.clear();
        FluxLoading.logger.debug("Cleared screenshot cache");
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public void onRenderHand(RenderHandEvent event) {
        if (ScreenshotHelper.suppressHandRendering) {
            event.setCanceled(true);
        }
    }
}
