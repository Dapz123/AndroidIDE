/*
 *  This file is part of AndroidIDE.
 *
 *  AndroidIDE is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  AndroidIDE is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with AndroidIDE.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.itsaky.androidide.lookup.internal;

import com.itsaky.androidide.lookup.Lookup;
import com.itsaky.androidide.lookup.ServiceRegisteredException;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Default implementation of {@link Lookup}.
 *
 * @author Akash Yadav
 */
public final class DefaultLookup implements Lookup {

  public static final Lookup INSTANCE = new DefaultLookup();

  private final Map<Class<?>, Key<?>> keyTable = new ConcurrentHashMap<>();
  private final Map<Key<?>, Object> services = new ConcurrentHashMap<>();

  private DefaultLookup() {}

  @Override
  public <T> void register(final Class<T> klass, final T instance) {
    final Key<T> key = key(klass);
    keyTable.put(klass, key);
    register(key, instance);
  }

  @Override
  public <T> void unregister(final Class<T> klass) {
    unregister(key(klass));
  }

  @Nullable
  @Override
  public <T> T lookup(final Class<T> klass) {
    return lookup(key(klass));
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> void register(final Key<T> key, final T instance) {
    final T existing = (T) services.put(key, instance);
    if (existing != null) {
      throw new ServiceRegisteredException();
    }
  }

  @Override
  public <T> void unregister(final Key<T> key) {
    services.remove(key);
  }

  @SuppressWarnings("unchecked")
  @Nullable
  @Override
  public <T> T lookup(final Key<T> key) {
    return (T) services.get(key);
  }

  @SuppressWarnings("unchecked")
  @NotNull
  private <T> Key<T> key(Class<T> klass) {
    final var key = keyTable.get(klass);
    if (key == null) {
      // Returning a new key instance will always make all the methods above return null
      return new Key<>();
    }

    return (Key<T>) key;
  }
}
