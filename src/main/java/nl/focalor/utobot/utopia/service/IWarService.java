package nl.focalor.utobot.utopia.service;

import nl.focalor.utobot.utopia.model.entity.War;

/**
 * Created by luigibanzato on 12/04/2015.
 */
public interface IWarService {

    public War startWar();
    public void endWar();
    public War getCurrentWar();
}
