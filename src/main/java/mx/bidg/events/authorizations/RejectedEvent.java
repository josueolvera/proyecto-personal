package mx.bidg.events.authorizations;

import mx.bidg.events.ModificationEvent;
import mx.bidg.model.Authorizations;

/**
 * @author Rafael Viveros
 * Created on 30/03/16.
 */
public class RejectedEvent implements ModificationEvent<Authorizations> {

    private Authorizations authorization;

    public RejectedEvent(Authorizations authorization) {
        this.authorization = authorization;
    }

    @Override
    public Authorizations getResource() {
        return authorization;
    }
}
