package com.RelistenAlexa.Handlers;

import com.RelistenAlexa.Playback;
import com.amazon.ask.Skill;
import com.amazon.ask.Skills;
import com.amazon.ask.SkillStreamHandler;


public class RelistenAlexaStreamHandler extends SkillStreamHandler {

    private static Skill getSkill() {
        return Skills.standard()
                .addRequestHandlers(
                        new CancelandStopIntentHandler(),
                        new HelpIntentHandler(),
                        new LaunchRequestHandler(),
                        new SessionEndedRequestHandler(),
                        new ShowIntentHandler())
                .build();
    }

    public RelistenAlexaStreamHandler() {
        super(getSkill());
    }

}