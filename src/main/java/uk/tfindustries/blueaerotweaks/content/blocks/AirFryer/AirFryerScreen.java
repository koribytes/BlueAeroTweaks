package uk.tfindustries.blueaerotweaks.content.blocks.AirFryer;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import uk.tfindustries.blueaerotweaks.BlueAeroTweaks;

public class AirFryerScreen extends AbstractContainerScreen<AirFryerMenu> {

    private static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(BlueAeroTweaks.MODID, "textures/gui/air_fryer/air_fryer_gui.png");
    private static final ResourceLocation ARROW_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(BlueAeroTweaks.MODID,"textures/gui/air_fryer/burn_progress.png");

    private static final ResourceLocation LIT_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(BlueAeroTweaks.MODID,"textures/gui/air_fryer/lit_progress.png");


    public AirFryerScreen(AirFryerMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(GUI_TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        renderProgressArrow(guiGraphics, x, y);
        renderProgressLit(guiGraphics, x, y);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            guiGraphics.blit(ARROW_TEXTURE,x + 79, y + 35, 0, 0, menu.getScaledArrowProgress(), 16, 24, 16);
        }
    }

    private void renderProgressLit(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            guiGraphics.blit(LIT_TEXTURE,x + 57, y + 37 + 14 - menu.getScaledLitProgress(), 0, 14 - menu.getScaledLitProgress(), 14, menu.getScaledLitProgress(), 14, 14);
        }
    }

}
