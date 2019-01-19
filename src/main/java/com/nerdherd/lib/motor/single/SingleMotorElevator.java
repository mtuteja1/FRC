/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.motor.single;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;

/**
 * Add your docs here.
 */
public class SingleMotorElevator extends SingleMotorTalonSRX {

  private double m_gravityFF;

  public SingleMotorElevator(int talonID, String subsystemName, boolean inversion, boolean sensorPhase) {
    super(talonID, subsystemName, inversion, sensorPhase);
    m_gravityFF = 0;
  }

  public void configGravityFF(double newGravityFF) {
    m_gravityFF = newGravityFF;
  }

  public void setPowerWithFF(double power) {
    m_motor.set(ControlMode.PercentOutput, power, DemandType.ArbitraryFeedForward, m_gravityFF);
  }

  public void setVoltageWithFF(double voltage) {
    m_motor.set(ControlMode.PercentOutput, voltage/12, DemandType.ArbitraryFeedForward, m_gravityFF);
  }

  @Override
  public void setPosition(double pos) {
    m_motor.set(ControlMode.Position, pos, DemandType.ArbitraryFeedForward, m_gravityFF);
  }
  
  @Override
  public void setPositionMotionMagic(double pos) {
    m_motor.set(ControlMode.MotionMagic, pos, DemandType.ArbitraryFeedForward, m_gravityFF);
  }

  @Override
  public void setVelocity(double vel) {
    m_motor.set(ControlMode.Velocity, vel, DemandType.ArbitraryFeedForward, m_gravityFF);
  }

}