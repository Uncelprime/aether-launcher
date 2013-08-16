package net.aetherteam.aether.launcher.gui.elements;

import net.aetherteam.aether.launcher.gui.forms.GuiForm;

import org.lwjgl.opengl.GL11;

public class GuiTextfield extends GuiElement {

	public static GuiTextfield activeTextfield;

	private GuiText text;

	private String realText;

	private boolean isPassword;

	private int cursorTimer;

	public GuiTextfield(GuiForm form, int x, int y, int width, int height, GuiText text, boolean isPassword) {
		super(form, x, y, width, height);

		this.text = text;
		this.realText = text.getString();
		this.isPassword = isPassword;
	}

	@Override
	public void render() {
		if (GuiTextfield.activeTextfield == this) {
			if (this.cursorTimer > 30) {
				GuiElement.renderColoredRect(this.getFadingX() + 6 + this.text.getWidth() + 3, this.y + 2, 5, this.height - 4, this.getHoveringColor());
			}

			this.cursorTimer = this.cursorTimer > 60 ? 0 : this.cursorTimer + 1;
		}

		if ((GuiTextfield.activeTextfield == this) || this.isMouseHovering()) {
			GuiElement.renderColoredRect(this.getFadingX(), this.y, this.width, this.height, this.getHoveringColor());
		} else {
			GuiElement.renderColoredRect(this.getFadingX(), this.y, this.width, this.height, this.getColor());
		}

		GL11.glColor4f(1, 1, 1, 1);
		this.text.render(this.getFadingX() + 8, this.y + ((this.height - this.text.getHeight()) / 2) + (this.isPassword ? 4 : 0));
	}

	@Override
	public void onMouseClick() {
		this.cursorTimer = 0;
		GuiTextfield.activeTextfield = this;
	}

	@Override
	public void onKey(char character) {
		if (GuiTextfield.activeTextfield == this) {
			if (character == 127) {
				if (this.text.getString().length() > 0) {
					this.text.setText(this.text.getString().substring(0, this.text.getString().length() - 1));
					this.realText = this.realText.substring(0, this.realText.length() - 1);
				}
			} else if ((character > 31) && (character < 126)) {
				char showCharacter = this.isPassword ? '*' : character;
				this.text.setText(this.text.getString() + showCharacter);
				this.realText += character;
			}
		}
	}

	public String getText() {
		return this.realText;
	}

}