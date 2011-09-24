/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ni.rio;

import com.sun.cldc.jna.Function;
import com.sun.cldc.jna.NativeLibrary;
import com.sun.cldc.jna.ptr.IntByReference;
import com.sun.squawk.platform.posix.LibCUtil;

/**
 *
 * @author ea149956
 */
public class VxWorks {
    /**
     * WARNING: Only use BOOT_CLEAR. Otherwise 5% chance of error on reboot.
     */
    /** causes the system to go through the countdown sequence and try to reboot VxWorks automatically. Memory is not cleared. */
    public final static int BOOT_NORMAL = 0x00;
    /** causes the system to display the VxWorks boot prompt and wait for user input to the boot ROM monitor. Memory is not cleared. */
    public final static int BOOT_NO_AUTOBOOT = (0x01);
    /** the same as BOOT_NORMAL, except that memory is cleared.  */
    public final static int BOOT_CLEAR = (0x02);
    /** the same as BOOT_NORMAL, except the countdown is shorter. */
    public final static int BOOT_QUICK_AUTOBOOT = (0x04);

    protected static final Function rebootFunction = NativeLibrary.getDefaultInstance().getFunction("reboot");

    private VxWorks() {
    }

    /**
     * Reboot cRIO
     *
     * WARNING: Only use BOOT_CLEAR. Otherwise 5% chance of error on reboot.
     * 
     * @param how
     */
    public static void reboot(int how) {
        rebootFunction.call1(how);
    }
    private static final Function taskDelayPtr = NativeLibrary.getDefaultInstance().getFunction("taskDelay");

    /**
     * Delay the Java VM. Calls taskDelay for the given number of ticks.
     *
     * WARNING: Only use if C/C++ tasks are getting starved. For ordinary delays use Timer.delay() or Thread.sleep().
     *
     * @param ticks (1 tick == 16ms)
     */
    public static void javaDelay(int ticks) {
        checkStatus(taskDelayPtr.call1(ticks));
    }

    /**
     * Have all Java threads yield the CPU to native threads.
     *
     * WARNING: Only use if C/C++ tasks are getting starved. For ordinary yield use Thread.yield().
     */
    public static void javaYield() {
        javaDelay(0);
    }

    private static final Function kernelTimeSlicePtr = NativeLibrary.getDefaultInstance().getFunction("kernelTimeSlice");

    /**
     * Set the vxworks time slice for round-robin scheduling of same-priority tasks.
     * Setting to zero will disable round-robin (bad!)
     * @param ticks (1 tick == 16ms)
     */
    public static void kernelTimeSlice(int ticks) {
        checkStatus(kernelTimeSlicePtr.call1(ticks));
    }

    private static final Function taskIdSelfPtr = NativeLibrary.getDefaultInstance().getFunction("taskIdSelf");
    private static final Function taskPrioritySetPtr = NativeLibrary.getDefaultInstance().getFunction("taskPrioritySet");
    private static final Function taskPriorityGetPtr = NativeLibrary.getDefaultInstance().getFunction("taskPriorityGet");

    /**
     * get the task ID of a running task
     *
     * @return task ID of the calling task
     */
    public static int taskIdSelf() {
        return taskIdSelfPtr.call0();
    }

    /**
     * change the priority of a task
     *
     * @param tid task ID
     * @param priority new priority
     */
    public static void taskPrioritySet(int tid, int priority) {
        checkStatus(taskPrioritySetPtr.call2(tid, priority));
    }

    private static IntByReference priorityPtr = new IntByReference(700);

    /**
     * examine the priority of a task
     * @param tid task ID
     * @return task priority
     */
    public static int taskPriorityGet(int tid) {
        checkStatus(taskPriorityGetPtr.call2(tid, priorityPtr.getPointer()));
        return priorityPtr.getValue();
    }

    private static void checkStatus(int status) {
        if (status != 0) {
            throw new RuntimeException("VxWorks error: " + LibCUtil.errno());
        }
    }
}
