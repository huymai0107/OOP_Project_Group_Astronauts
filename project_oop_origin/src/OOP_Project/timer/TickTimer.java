package OOP_Project.timer;

public class TickTimer {

	private float tick, tickTarget;
	
	public TickTimer(float tickTarget) {
		this.tickTarget = tickTarget;
		this.tick = 0;
	}
	
	public void tick(double delta) {
		if (tick <= tickTarget) {
			tick += 1 * delta;
		}
	}
	
	public boolean isEventReady() {
		if (tick >= tickTarget) {
			resetTimer();
			return true;
		}
		return false;
	}
	
	private void resetTimer() {
		tick = 0;
	}
}
