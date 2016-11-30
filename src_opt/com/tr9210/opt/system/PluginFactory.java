package com.tr9210.opt.system;

import java.util.LinkedHashMap;
import java.util.Map;

import com.tr9210.opt.osi.dm.IOSComposer;

/** Return concrete implementations for specific, known interfaces. */
public final class PluginFactory {
  
  /**
   Read in configuration data that maps names of interfaces to names of 
   corresponding concrete implementation classes. Called early upon startup, 
   before any implementations are needed by the rest of the program.
   
   <P>Example of a possible entry in such a config file :
   myapp.TimeSource = myapp.TimeSourceOneDayAdvance
  */
  public static void init(){
    //elided
    //perhaps a properties file is read, perhaps some other source is used
    fImplementations.put("OSIComposerDA_01","com.tr9210.opt.osi.im01.ComposerImpl01");
  }
  
  /*
  * Another variation: allow the caller to swap in different implementation classes
  * at runtime, after calling init. This allows testing code to swap in various 
  * implementations.
  */
  
  /**
   Return the concrete implementation of the TimeSource interface.
  */
  public static IOSComposer getComposer() {
    String implClassName = fImplementations.get("OSIComposerDA_01");
    System.out.println("PluginFactory.IOSComposer impleClassName = "+implClassName);
    IOSComposer result = (IOSComposer)buildObject(implClassName);
    return result;
  }
  
  // PRIVATE
  
  /**
   Map the name of an interface to the name of a corresponding concrete 
   implementation class. 
  */
  private static final Map<String, String> fImplementations = new LinkedHashMap<>();
  
  private static Object buildObject(String aClassName){
    Object result = null;
    try {
      //note that, with this style, the implementation needs to have a
      //no-argument constructor!
      Class implClass = Class.forName(aClassName);
      result = implClass.newInstance();
    }
    catch (ClassNotFoundException ex) {
      //elided
    }
    catch (InstantiationException ex) {
      //elided
    }
    catch (IllegalAccessException ex) {
      //elided
    }
    return result;
  }
}
 
