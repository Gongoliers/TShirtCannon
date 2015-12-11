package org.usfirst.frc5112.TShirtCannon;

import edu.wpi.first.wpilibj.SpeedController;

public class SixWheels {

	SpeedController motors[][] = new SpeedController[2][3];
	public double m_sensitivity = 0.5;
	int invertValues[] = new int[2];

	public static final int LEFT = 0, RIGHT = 1;

	public void setInverted(int side, boolean inverted) {
		if (inverted) {
			invertValues[side] = -1;
		} else {
			invertValues[side] = 1;
		}
	}

	public SixWheels(SpeedController fL, SpeedController mL, SpeedController rL, SpeedController fR, SpeedController mR,
			SpeedController rR) {
		motors[0][0] = fL;
		motors[0][1] = mL;
		motors[0][2] = rL;
		motors[1][0] = fR;
		motors[1][1] = mR;
		motors[1][2] = rR;
	}

	public void drive(double outputMagnitude, double curve) {
		double leftOutput, rightOutput;
		if (curve < 0) {
			double value = Math.log(-curve);
			double ratio = (value - m_sensitivity) / (value + m_sensitivity);
			if (ratio == 0) {
				ratio = .0000000001;
			}
			leftOutput = outputMagnitude / ratio;
			rightOutput = outputMagnitude;
		} else if (curve > 0) {
			double value = Math.log(curve);
			double ratio = (value - m_sensitivity) / (value + m_sensitivity);
			if (ratio == 0) {
				ratio = .0000000001;
			}
			leftOutput = outputMagnitude;
			rightOutput = outputMagnitude / ratio;
		} else {
			leftOutput = outputMagnitude;
			rightOutput = outputMagnitude;
		}
		tankDrive(leftOutput, rightOutput);
	}

	public void tankDrive(double speedLeft, double speedRight) {
		if (Math.abs(speedLeft) > 1) {
			speedLeft = Math.copySign(1, speedLeft);
		}
		if (Math.abs(speedRight) > 1) {
			speedRight = Math.copySign(1, speedRight);
		}
		// Left motors
		for (int i = 0; i < motors[0].length; i++) {
			motors[0][i].set(speedLeft * invertValues[LEFT]);
		}
		// Left motors
		for (int i = 0; i < motors[1].length; i++) {
			motors[1][i].set(speedRight * invertValues[RIGHT]);
		}
	}
}
