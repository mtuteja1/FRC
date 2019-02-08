/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.motor.commands.mechanisms;

import com.nerdherd.lib.motor.single.mechanisms.GravityAffectedMechanism;
import com.nerdherd.lib.sensor.HallSensor;

import edu.wpi.first.wpilibj.command.Command;

public class ZeroMechanismWithHallEffect extends Command {

  private GravityAffectedMechanism m_mechanism;
  private HallSensor m_hallSensor;
  private double m_rate;

  public ZeroMechanismWithHallEffect(GravityAffectedMechanism mechanism, 
    HallSensor hallEffectSensor, double descentRate) {
      m_mechanism = mechanism;
      m_hallSensor = hallEffectSensor;
      m_rate = descentRate;
      requires(m_mechanism);

  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    m_mechanism.setVoltageWithFF(m_rate);

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return m_hallSensor.getValue();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    m_mechanism.resetEncoder();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}