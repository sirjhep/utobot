package nl.focalor.utobot.utopia.handler;

import nl.focalor.utobot.base.input.CommandInput;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.util.ReflectionUtil;
import nl.focalor.utobot.utopia.model.entity.War;
import nl.focalor.utobot.utopia.service.IWarService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Created by luigibanzato on 12/04/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class WarEndHandlerTest {

    @InjectMocks
    private WarEndHandler warEndHandler;

    @Mock
    private IWarService warService;

    @Test
    public void testEndWar(){
        //Setup
        War war = new War();
        war.setId(1l);
        war.setStartDate(new Date());
        when(warService.getCurrentWar()).thenReturn(war);
        doNothing().when(warService).endWar();

        // Test
        IResult reply = warEndHandler.handleCommand(CommandInput.createFor("oneguy", "!endwar"));

        // Verify
        String msg = ReflectionUtil.getField(reply, "message", String.class);
        assertTrue(msg.equals("War Ended. War Id: 1."));
    }

}