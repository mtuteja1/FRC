/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.motor.single;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.nerdherd.lib.misc.NerdyMath;

import badlog.lib.BadLog;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class SingleMotorArm extends SingleMotorTalonSRX {

  private double m_gravityFF, m_angleRatio, m_angleOffset;

  public SingleMotorArm(int talonID, String subsystemName, boolean inversion, boolean sensorPhase) {
    super(talonID, subsystemName, inversion, sensorPhase);
    m_gravityFF = 0;
    m_angleRatio = 1;
    m_angleOffset = 0;
  }

  public void configGravityFF(double newGravityFF) {
    m_gravityFF = newGravityFF;
  }

  public void configAngleRatio(double newAngleRatio) {
    m_angleRatio = newAngleRatio;
  }

  public void configAngleOffset(double newAngleOffset) {
    m_angleOffset = newAngleOffset;
  }

  public void configAngleConversion(double newAngleRatio, double newAngleOffset) {
    m_angleRatio = newAngleRatio;
    m_angleOffset = newAngleOffset;
  }

  public void setPowerWithFF(double power) {
    m_motor.set(ControlMode.PercentOutput, power, DemandType.ArbitraryFeedForward, 
      m_gravityFF * Math.cos(NerdyMath.degreesToRadians(getAngle())));
  }

  public void setVoltageWithFF(double voltage) {
    m_motor.set(ControlMode.PercentOutput, voltage/12, DemandType.ArbitraryFeedForward, 
      m_gravityFF * Math.cos(NerdyMath.degreesToRadians(getAngle())));
  }

  public void setAngle(double angle) {
    m_motor.set(ControlMode.Position, angleToTicks(angle), DemandType.ArbitraryFeedForward, 
      m_gravityFF * Math.cos(NerdyMath.degreesToRadians(getAngle())));
  }

  @Override
  public void setPosition(double pos) {
    m_motor.set(ControlMode.Position, pos, DemandType.ArbitraryFeedForward, 
      m_gravityFF * Math.cos(NerdyMath.degreesToRadians(getAngle())));
  }

  public void setAngleMotionMagic(double angle) {
    m_motor.set(ControlMode.MotionMagic, angleToTicks(angle), DemandType.ArbitraryFeedForward, 
      m_gravityFF * Math.cos(NerdyMath.degreesToRadians(getAngle())));
  }
  
  @Override
  public void setPositionMotionMagic(double pos) {
    m_motor.set(ControlMode.MotionMagic, pos, DemandType.ArbitraryFeedForward, 
      m_gravityFF * Math.cos(NerdyMath.degreesToRadians(getAngle())));
  }

  @Override
  public void setVelocity(double vel) {
    m_motor.set(ControlMode.Velocity, vel, DemandType.ArbitraryFeedForward, 
      m_gravityFF * Math.cos(NerdyMath.degreesToRadians(getAngle())));
  }

  public double angleToTicks(double angle) {
    return (angle - m_angleOffset) / m_angleRatio;
  }

  public double ticksToAngle(double ticks) {
    return (m_angleRatio * ticks) + m_angleOffset;
  }

  public double getAngle() {
    return ticksToAngle(this.getPosition());
  }

  @Override
  public void reportToSmartDashboard() {
    super.reportToSmartDashboard();
    SmartDashboard.putNumber(m_name + " Angle", getAngle());
  }

  @Override
  public void initLoggingData() {
    super.initLoggingData();
    BadLog.createTopic(m_name + "/Angle", "deg", () -> getAngle());
  }

}
