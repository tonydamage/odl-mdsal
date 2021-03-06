/*
 * Copyright (c) 2014 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.mdsal.dom.spi.store;

/**
 * Combination of a {@link DOMStoreReadTransaction} and {@link DOMStoreWriteTransaction}.
 */
public interface DOMStoreReadWriteTransaction extends DOMStoreReadTransaction, DOMStoreWriteTransaction {

}
