package nl.focalor.utobot.utopia.handler;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import nl.focalor.utobot.base.input.CommandInput;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.MultiReplyResult;
import nl.focalor.utobot.base.input.ReplyResult;
import nl.focalor.utobot.base.input.handler.AbstractCommandHandler;
import nl.focalor.utobot.base.model.service.IPersonService;
import nl.focalor.utobot.utopia.model.entity.Attack;
import nl.focalor.utobot.utopia.service.IAttackService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author focalor
 */
@Component
public class ArmiesHandler extends AbstractCommandHandler {
	public static final String COMMAND_NAME = "armies";

	@Autowired
	private IAttackService attackService;
	@Autowired
	private IPersonService personService;

	public ArmiesHandler() {
		super(COMMAND_NAME);
	}

	@Override
	public IResult handleCommand(CommandInput event) {
		List<Attack> attacks = attackService.findAll();
		if (attacks.isEmpty()) {
			return new ReplyResult("No armies found");
		}
		//@formatter:off
		List<String> msgs = attacks.stream()
				.sorted((left, right) -> left.getReturnDate().compareTo(right.getReturnDate()))
				.map(this::toMessage)
				.collect(Collectors.toList());
		//@formatter:on!

		return new MultiReplyResult(msgs);
	}

	private String toMessage(Attack attack) {
		String attacker;
		attacker = attack.getPerson().getName();

		long deltaSeconds = (attack.getReturnDate().getTime() - new Date().getTime()) / 1000;
		int deltaMinutes = (int) (deltaSeconds / 60);
		int deltahours = deltaMinutes / 60;

		int seconds = (int) (deltaSeconds % 60);
		int minutes = deltaMinutes % 60;

		StringBuilder builder = new StringBuilder();
		builder.append(attacker);
		builder.append("'s army out for ");
		builder.append(deltahours);
		builder.append("h ");
		builder.append(minutes);
		builder.append("m ");
		builder.append(seconds);
		builder.append("s");
		return builder.toString();
	}
}
