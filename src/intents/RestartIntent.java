package intents;

import core.LevelManager;
import core.ToastManager;
import entities.Entity;

public class RestartIntent implements Intent {
	@Override
	public void handle(Entity entity) {
		ToastManager.getInstance().hide();
		LevelManager.getInstance().load();
	}
}
