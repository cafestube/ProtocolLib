/*
 *  ProtocolLib - Bukkit server library that allows access to the Minecraft protocol.
 *  Copyright (C) 2012 Kristian S. Stangeland
 *
 *  This program is free software; you can redistribute it and/or modify it under the terms of the 
 *  GNU General Public License as published by the Free Software Foundation; either version 2 of 
 *  the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 *  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 *  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with this program; 
 *  if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 
 *  02111-1307 USA
 */

package com.comphenix.protocol;

import java.util.Set;

import org.bukkit.plugin.Plugin;

import com.comphenix.protocol.async.AsyncListenerHandler;
import com.comphenix.protocol.error.ErrorReporter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;

/**
 * Represents a asynchronous packet handler.
 * 
 * @author Kristian
 */
public interface AsynchronousManager {
	/**
	 * Registers an asynchronous packet handler.
	 * <p>
	 * To start listening asynchronously, pass the getListenerLoop() runnable to a different thread.
	 * @param listener - the packet listener that will recieve these asynchronous events.
	 * @return An asynchrouns handler.
	 */
	public abstract AsyncListenerHandler registerAsyncHandler(PacketListener listener);

	/**
	 * Unregisters and closes the given asynchronous handler.
	 * @param handler - asynchronous handler.
	 */
	public abstract void unregisterAsyncHandler(AsyncListenerHandler handler);

	/**
	 * Unregisters every asynchronous handler associated with this plugin.
	 * @param plugin - the original plugin.
	 */
	public void unregisterAsyncHandlers(Plugin plugin);
	
	/**
	 * Retrieves a immutable set containing the ID of the sent server packets that will be 
	 * observed by the asynchronous listeners.
	 * @return Every filtered server packet.
	 */
	public abstract Set<Integer> getSendingFilters();

	/**
	 * Retrieves a immutable set containing the ID of the recieved client packets that will be
	 * observed by the asynchronous listeners.
	 * @return Every filtered client packet.
	 */
	public abstract Set<Integer> getReceivingFilters();

	/**
	 * Determine if a given synchronous packet has asynchronous listeners.
	 * @param packet - packet to test.
	 * @return TRUE if it does, FALSE otherwise.
	 */
	public abstract boolean hasAsynchronousListeners(PacketEvent packet);

	/**
	 * Retrieve the default packet stream.
	 * @return Default packet stream.
	 */
	public abstract PacketStream getPacketStream();

	/**
	 * Retrieve the default error reporter.
	 * @return Default reporter.
	 */
	public abstract ErrorReporter getErrorReporter();

	/**
	 * Remove listeners, close threads and transmit every delayed packet.
	 */
	public abstract void cleanupAll();

	/**
	 * Signal that a packet is ready to be transmitted.
	 * <p>
	 * This should only be called if {@link com.comphenix.protocol.async.AsyncMarker#incrementProcessingDelay() AsyncMarker.incrementProcessingDelay()}
	 * has been called previously.
	 * @param packet - packet to signal.
	 */
	public abstract void signalPacketTransmission(PacketEvent packet);
}