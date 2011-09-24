/**
   \file       NiRioEntryPoints.h
   \author     Erik Hons <erik.hons@ni.com>
   \date       12/14/2004

   \brief Declarations for RIO services client DLL entry points

   Intended to be called from a C client, or the LabVIEW
   interface.

   � Copyright 2004. National Instruments. All rights reserved.
*/

package com.ni.rio;

import com.sun.cldc.jna.*;
import com.sun.cldc.jna.ptr.IntByReference;

public class NiRioSrv implements NiRioConstants
{
   // ---------------------------
   // Device Handles:

   // open(): Get a device handle for the specified RIO resource. The
   // handle should be released when no longer needed.
   //
   // returns: tNIRIO_u32 device handle (0 if error)
   //
	private static final Function openFn = NativeLibrary.getDefaultInstance().getFunction("NiRioSrv_device_open");
	public static int open(String resource, NiRioStatus status)
   {
      Pointer resourcePtr = Pointer.createStringBuffer(resource);
      int deviceHandle = openFn.call2(resourcePtr, status.getPointer());
      resourcePtr.free();
      status.assertNonfatal();
      if (deviceHandle == 0)
      {
         status.setStatus(NiRioStatus.kRIOStatusInvalidHandle);
      }
      return deviceHandle;
	}

   // close(): Release the specified device handle.
   //
	private static final Function closeFn = NativeLibrary.getDefaultInstance().getFunction("NiRioSrv_device_close");
	public static void close(int hClient, NiRioStatus status)
   {
      closeFn.call2(hClient, status.getPointer());
      status.assertNonfatal();
	}


   // Added in 230
   //
   // Client must freeString() any non-NULL return value.
   //
//kNIRIOSRVExportPre char*
//kNIRIOSRVCCall NiRioSrv_device_getString(  tRioDeviceHandle  hClient,
//                                           tRioDeviceAttrStr attribute,
//                                           tRioStatusCode*   status)
//kNIRIOSRVExportPost;
//
//typedef char*
//(kNIRIOSRVCCall *NiRioSrv_device_getStringFn)(
//                                           tRioDeviceHandle  hClient,
//                                           tRioDeviceAttrStr attribute,
//                                           tRioStatusCode*   status);

	private static final Function set32Fn = NativeLibrary.getDefaultInstance().getFunction("NiRioSrv_device_set32");
	public static void set32(int hClient, int attribute, int value, NiRioStatus status)
   {
      set32Fn.call4(hClient, attribute, value, status.getPointer());
      status.assertNonfatal();
	}

   // Added in 230
   //
   //
	private static final Function setStringFn = NativeLibrary.getDefaultInstance().getFunction("NiRioSrv_device_setString");
	public static void setString(int hClient, int attribute, String value, NiRioStatus status)
   {
      Pointer valuePtr = Pointer.createStringBuffer(value);
      setStringFn.call4(hClient, attribute, valuePtr, status.getPointer());
      valuePtr.free();
      status.assertNonfatal();
	}

   // ---------------------------
   // Device Driver Configuration (resources):
   //
   // The driver must be configured to match whatever resources are
   // present on the FPGA device so that the RIO driver can provide
   // platform independant, thread-safe mechanisms to access them.
   //
   // Resources are specified "script-style". Each resource is specified,
   // one at a time, with a configAddXXX() call. Once specified,
   // resources are are then enabled as a group using configSet().
   //

   // configSet(): All resources "added" since the device handle was
   // obtained, or since the last configSet() call, are enabled for use.
   //
   // attr: must be 0
   //
	private static final Function configSetFn = NativeLibrary.getDefaultInstance().getFunction("NiRioSrv_device_configSet");
	public static void configSet(int hClient, int attribute, NiRioStatus status)
   {
      configSetFn.call3(hClient, attribute, status.getPointer());
      status.assertNonfatal();
	}

   // configAddFifoInput(): Add an input fifo device resource.
   //
   // channel: logical channel for fifo (DMA channel)
   //
	private static final Function configAddFifoInputExFn = NativeLibrary.getDefaultInstance().getFunction("NiRioSrv_device_configAddFifoInputEx");
	public static void configAddFifoInputEx(int hClient, int channel, int baseAddress, int depthInSamples, int version, NiRioStatus status)
   {
      configAddFifoInputExFn.call6(hClient, channel, baseAddress, depthInSamples, version, status.getPointer());
      status.assertNonfatal();
	}

   // configAddFifoOutput(): Add an output fifo device resource.
   //
   // channel: logical channel for fifo (DMA channel)
   //
	private static final Function configAddFifoOutputExFn = NativeLibrary.getDefaultInstance().getFunction("NiRioSrv_device_configAddFifoOutputEx");
	public static void configAddFifoOutputEx(int hClient, int channel, int baseAddress, int depthInSamples, int version, NiRioStatus status)
   {
      configAddFifoOutputExFn.call6(hClient, channel, baseAddress, depthInSamples, version, status.getPointer());
      status.assertNonfatal();
	}

   // ---------------------------
   // Fifo Operations:

	private static final Function fifoConfigFn = NativeLibrary.getDefaultInstance().getFunction("NiRioSrv_device_fifoConfig");
	public static void fifoConfig(int hClient, int channel, int fifoDepthInElements, NiRioStatus status)
   {
      fifoConfigFn.call4(hClient, channel, fifoDepthInElements, status.getPointer());
      status.assertNonfatal();
	}

	private static final Function fifoStartFn = NativeLibrary.getDefaultInstance().getFunction("NiRioSrv_device_fifoStart");
	public static void fifoStart(int hClient, int channel, NiRioStatus status)
   {
      fifoStartFn.call3(hClient, channel, status.getPointer());
      status.assertNonfatal();
	}

	private static final Function fifoStopFn = NativeLibrary.getDefaultInstance().getFunction("NiRioSrv_device_fifoStop");
	public static void fifoStop(int hClient, int channel, NiRioStatus status)
   {
      fifoStopFn.call3(hClient, channel, status.getPointer());
      status.assertNonfatal();
	}

	private static final Function fifoReadFn = NativeLibrary.getDefaultInstance().getFunction("NiRioSrv_device_fifoRead");
	public static void fifoRead(int hClient, int channel, Pointer buf, int num, int timeout, IntByReference read, IntByReference remaining, NiRioStatus status)
   {
      fifoReadFn.call8(hClient,
         channel,
         buf.address().toUWord().toPrimitive(),
         num,
         timeout,
         read.getPointer().address().toUWord().toPrimitive(),
         remaining.getPointer().address().toUWord().toPrimitive(),
         status.getPointer());
      status.assertNonfatal();
	}

//void
//NiRioSrv_device_fifoWrite(       tRioDeviceHandle  hClient,
//                                                tNIRIO_u32        channel,
//                                                const tNIRIO_u32* buf,
//                                                tNIRIO_u32        num,
//                                                tNIRIO_u32        timeout,
//                                                tNIRIO_u32*       remaining,
//                                                tRioStatusCode*   status)
//;

	private static final Function fifoStopAllFn = NativeLibrary.getDefaultInstance().getFunction("NiRioSrv_device_fifoStopAll");
	public static void fifoStopAll(int hClient, NiRioStatus status)
   {
      fifoStopAllFn.call2(hClient, status.getPointer());
      status.assertNonfatal();
	}

   // ---------------------------
   // I/O:

	private static final Function poke32Fn = NativeLibrary.getDefaultInstance().getFunction("NiRioSrv_device_poke32");
	public static void poke32(int hClient, int offset, int value, NiRioStatus status)
   {
//      System.out.print("write offset = 0x");
//      System.out.println(Long.toString(offset, 16));
//      System.out.print("value = 0x");
//      System.out.println(Long.toString(((long)value) & 0xFFFFFFFFL, 16));
      poke32Fn.call4(hClient, offset, value, status.getPointer());
      status.assertNonfatal();
	}

	private static final Function poke16Fn = NativeLibrary.getDefaultInstance().getFunction("NiRioSrv_device_poke16");
	public static void poke16(int hClient, int offset, short value, NiRioStatus status)
   {
//      System.out.print("write offset = 0x");
//      System.out.println(Long.toString(offset, 16));
//      System.out.print("value = 0x");
//      System.out.println(Long.toString(((long)value) & 0xFFFFFFFFL, 16));
      poke16Fn.call4(hClient, offset, value, status.getPointer());
      status.assertNonfatal();
	}

	private static final Function peek32Fn = NativeLibrary.getDefaultInstance().getFunction("NiRioSrv_device_peek32");
	public static int peek32(int hClient, int offset, NiRioStatus status)
   {
//      System.out.print("read offset = 0x");
//      System.out.println(Long.toString(offset, 16));
      int value = peek32Fn.call3(hClient, offset, status.getPointer());
//      System.out.print("value = 0x");
//      System.out.println(Long.toString(((long)value) & 0xFFFFFFFFL, 16));
      status.assertNonfatal();
      return value;
	}

   // ---------------------------
   // IRQs:

	private static final Function irqReserveFn = NativeLibrary.getDefaultInstance().getFunction("NiRioSrv_device_irqReserve");
	public static void irqReserve(int hClient, IntByReference context, NiRioStatus status)
   {
      irqReserveFn.call3(hClient, context.getPointer().address().toUWord().toPrimitive(), status.getPointer());
      status.assertNonfatal();
	}

   private static final Function irqUnreserveFn = NativeLibrary.getDefaultInstance().getFunction("NiRioSrv_device_irqUnreserve");
	public static void irqUnreserve(int hClient, IntByReference context, NiRioStatus status)
   {
      irqUnreserveFn.call3(hClient, context.getPointer().address().toUWord().toPrimitive(), status.getPointer());
      status.assertNonfatal();
	}

   private static final Function irqWaitFn = NativeLibrary.getDefaultInstance().getFunction("NiRioSrv_device_irqWait");
	public static int irqWait(int hClient, IntByReference context, int irqs, int timeout, NiRioStatus status)
   {
      int value = irqWaitFn.call5(hClient, context.getPointer().address().toUWord().toPrimitive(), irqs, timeout, status.getPointer());
      status.assertNonfatal();
      return value;
	}

}