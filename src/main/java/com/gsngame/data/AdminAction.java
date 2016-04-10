package com.gsngame.data;

public enum AdminAction {

	APROVE(2), REJECT(3);

	private final int action;

	AdminAction(int action) {
		this.action = action;
	}

	public int getStatus() {
		return this.action;
	}

	public static AdminAction fromValue(int value) {
		for (AdminAction adminAction : AdminAction.values()) {
			if (adminAction.action==value) {
				return adminAction;
			}
		}
		throw new IllegalArgumentException("Invalid Action: " + AdminAction.values());
	}
	
}
