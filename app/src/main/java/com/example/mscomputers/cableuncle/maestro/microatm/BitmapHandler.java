package com.example.mscomputers.cableuncle.maestro.microatm;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

/**
 * A handler for bitmap images.
 */
public final class BitmapHandler {
    private static final int[] MASKS = {0x000000ff, 0x000000ff, 0x000000ff};

    private final Bitmap bitmap;

    /**
     * Creates an instance of <code>BitmapHandler</code> with given <code>Bitmap</code>.
     *
     * @param bitmap the given bitmap to handle, not null
     * @see Bitmap
     */
    public BitmapHandler(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    /**
     * Converts the <code>Bitmap</code> data to PNG image format and returns a <code>FileHandler</code>.
     *
     * @return a FileHandler, not null
     * @see FileHandler
     */
    public FileHandler toPng() {
        return new FileHandler(convert(bitmap, "png"));
    }

    /**
     * Converts the <code>Bitmap</code> data to GIF image format and returns a <code>FileHandler</code>.
     *
     * @return a FileHandler, not null
     * @see FileHandler
     */
    public FileHandler toGif() {
        return new FileHandler(convert(bitmap, "gif"));
    }

    /**
     * Converts the <code>Bitmap</code> data to JPEG image format and returns a <code>FileHandler</code>.
     *
     * @return a FileHandler, not null
     * @see FileHandler
     */
    public FileHandler toJpg() {
        return new FileHandler(convert(bitmap, "jpeg"));
    }

    /**
     * Returns the enclosed <code>Bitmap</code> data.
     *
     * @return a FileHandler, not null
     * @see Bitmap
     */
    public Bitmap asBitmap() {
        return bitmap;
    }

    /**
     * Converts the given <code>Bitmap</code> to the specified image format and returns it as byte array.
     *
     * @param bitmap the given bitmap, not null
     * @param format the given target image format, not null
     * @return the converted data in byte array format, not null
     */
    byte[] yourBytes;
     byte[] convert(Bitmap bitmap, String format) {
        
    	final int width = bitmap.getWidth();
        final int height = bitmap.getHeight();
        
        
       // System.out.print("decrypt byte array size"+yourbytearray.length);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        try {
          try {
			out = new ObjectOutputStream(bos);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
          try {
			out.writeObject(bitmap);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          try {
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          yourBytes = bos.toByteArray();
        
        } finally {
          try {
            bos.close();
          } catch (IOException ex) {
            // ignore close exception
          }
        }
        
            return yourBytes;
       
    }
}
