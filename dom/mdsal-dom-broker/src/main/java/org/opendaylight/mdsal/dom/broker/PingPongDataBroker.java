/*
 * Copyright (c) 2014 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.mdsal.dom.broker;

import org.opendaylight.mdsal.dom.spi.ForwardingDOMDataBroker;

import org.opendaylight.mdsal.common.api.TransactionChainListener;
import org.opendaylight.mdsal.dom.api.DOMDataBroker;
import org.opendaylight.mdsal.dom.api.DOMDataTreeChangeListener;
import org.opendaylight.mdsal.dom.api.DOMDataTreeChangeService;
import org.opendaylight.mdsal.dom.api.DOMDataTreeIdentifier;
import com.google.common.base.Preconditions;
import javax.annotation.Nonnull;
import org.opendaylight.yangtools.concepts.ListenerRegistration;

/**
 * An implementation of a {@link DOMDataBroker}, which forwards most requests to a delegate.
 *
 * Its interpretation of the API contract is somewhat looser, specifically it does not
 * guarantee transaction ordering between transactions allocated directly from the broker
 * and its transaction chains.
 */
public final class PingPongDataBroker extends ForwardingDOMDataBroker implements AutoCloseable, DOMDataTreeChangeService {
    private final DOMDataBroker delegate;

    /**
     * Instantiate a new broker, backed by the the specified delegate
     * {@link DOMDataBroker}.
     *
     * @param delegate Backend broker, may not be null.
     */
    public PingPongDataBroker(@Nonnull final DOMDataBroker delegate) {
        this.delegate = Preconditions.checkNotNull(delegate);
    }

    @Override
    protected DOMDataBroker delegate() {
        return delegate;
    }

    @Override
    public PingPongTransactionChain createTransactionChain(final TransactionChainListener listener) {
        return new PingPongTransactionChain(delegate, listener);
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub
    }

    @Override
    public <L extends DOMDataTreeChangeListener> ListenerRegistration<L> registerDataTreeChangeListener(final DOMDataTreeIdentifier treeId, final L listener) {
        if (delegate instanceof DOMDataTreeChangeService) {
            return ((DOMDataTreeChangeService)delegate).registerDataTreeChangeListener(treeId, listener);
        }

        throw new UnsupportedOperationException("Delegate " + delegate + " does not support required functionality");
    }
}
