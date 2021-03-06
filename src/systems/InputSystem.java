/*
 * @author Thad Benjaponpitak
 */
package systems;

import components.InputComponent;
import core.EntityManager;
import core.InputManager;
import entities.Entity;
import intents.Intent;
import javafx.scene.input.KeyCode;
import utils.ComponentNotFoundException;

public class InputSystem extends EntitySystem {
	public InputSystem() {
		super(14);
	}

	@Override
	public void update(double deltaTime) {
		handleGlobalIntents();
		handleEntityIntents();
		InputManager.getInstance().clearTriggered();
	}

	private void handleGlobalIntents() {
		for (KeyCode keyCode : InputManager.getInstance().getPressed()) {
			Intent intent = InputManager.getInstance().getPressedIntent(keyCode);
			if (intent != null)
				intent.handle(null);
		}
		for (KeyCode keyCode : InputManager.getInstance().getTriggered()) {
			Intent intent = InputManager.getInstance().getTriggeredIntent(keyCode);
			if (intent != null)
				intent.handle(null);
		}
	}

	private void handleEntityIntents() {
		for (Entity entity : EntityManager.getInstance().getEntities()) {
			try {
				InputComponent inputComponent = (InputComponent) entity.getComponent(InputComponent.class);
				for (KeyCode keyCode : InputManager.getInstance().getPressed()) {
					Intent intent = inputComponent.getPressedIntent(keyCode);
					if (intent != null)
						intent.handle(entity);
				}
				for (KeyCode keyCode : InputManager.getInstance().getTriggered()) {
					Intent intent = inputComponent.getTriggeredIntent(keyCode);
					if (intent != null)
						intent.handle(entity);
				}
			} catch (ComponentNotFoundException e) {
				continue;
			}
		}
	}
}
