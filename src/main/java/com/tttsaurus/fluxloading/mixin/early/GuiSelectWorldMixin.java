package com.tttsaurus.fluxloading.mixin.early;

import com.tttsaurus.fluxloading.util.ScreenshotHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.SaveFormatComparator;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.client.gui.GuiSelectWorld$List")
public class GuiSelectWorldMixin {

    @ModifyVariable(
            method = "drawSlot(IIIILnet/minecraft/client/renderer/Tessellator;II)V",
            at = @At("HEAD"),
            index = 2 // p_148126_2_
    )
    private int modifyDrawSlotX(int original) {
        //SaveFormatComparator saveformatcomparator = (SaveFormatComparator)((GuiSelectWorld)(Object)this).field_146639_s.get(p_148126_1_);
        //String s = saveformatcomparator.getDisplayName();

        return original + 36; // or whatever modification you need
    }

    @Shadow(remap = false)
    @Final
    GuiSelectWorld this$0;

    @Inject(
            method = "drawSlot(IIIILnet/minecraft/client/renderer/Tessellator;II)V",
            at = @At("HEAD")
    )
    private void onDrawSlot(
            int index, int x, int y, int height,
            Tessellator tessellator, int mouseX, int mouseY,
            CallbackInfo ci) {

        //GuiSelectWorld screen = Minecraft.getMinecraft().currentScreen instanceof GuiSelectWorld
        //        ? (GuiSelectWorld) Minecraft.getMinecraft().currentScreen : null;

        //if (screen == null) return;

        // Get the world list and folder name
        java.util.List saves = ((GuiSelectWorldAccessor)this$0).getField_146639_s();
        if (index < 0 || index >= saves.size()) return;

        SaveFormatComparator save = (SaveFormatComparator) saves.get(index);
        String folderName = save.getFileName();

        ResourceLocation screenshot = ScreenshotHelper.getOrLoadScreenshot(folderName);
        if (screenshot != null) {
            Minecraft mc = Minecraft.getMinecraft();
            mc.getTextureManager().bindTexture(screenshot);
            Gui.func_146110_a(x - 36, y, 0, 0, 32, 32, 32, 32);
        }
    }
}

