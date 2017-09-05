package ca.sukhsingh.actions.on.google;

import ca.sukhsingh.actions.on.google.response.Response;
import ca.sukhsingh.actions.on.google.response.data.google.RichResponse.SimpleResponse;
import ca.sukhsingh.actions.on.google.response.data.google.RichResponse.Suggestion;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by sukhsingh on 2017-08-29.
 */
@RunWith(MockitoJUnitRunner.class)
public class ApiAiAppTest {

    @InjectMocks
    private ApiAiApp app;

    /*
        test with tell(null)
        test with tell(null,null)
        test with tell(null, "something")
        test with tell("something", null)
        test with tell (textToSpeech SSML)

        test with ask(null)
        test with ask(null,null)
        test with ask(null, "something")
        test with ask("something", null)
        test with ask(textToSpeech SSML)

    */
    /**
     * Describes the behavior for ApiAiApp tell method.
     */
    @Test
    public void appTellTest() {
        Response response = app.tell("hello");
        assertNotNull(response);
//        {
//            "speech": "hello",
//            "data": {
//                "google": {
//                    "expect_user_response": false,
//                    "is_ssml": false,
//                    "no_input_prompts": []
//                }
//            },
//            "contextOut": []
//        }
        assertSpeech(response, "hello");
        assertNotNull(response.getData());
        assertNotNull(response.getData().getGoogle());
        assertExpectUserResponseFalse(response);
        assertIsSsmlFalse(response);
        assertNotNull(response.getData().getGoogle().noInputPrompts);
        assertNotNull(response.getContextOut());

//        {
//            'speech': 'hello',
//            'data': {
//            'google': {
//                'expect_user_response': false,
//                'rich_response': {
//                    'items': [
//                    {
//                        'simple_response': {
//                        'text_to_speech': 'hello',
//                                'display_text': 'hi'
//                    }
//                    }
//            ],
//                    'suggestions': []
//                }
//            }
//        },
//            'contextOut': []
//        }

        response = app.tell("hello", "hi");
        assertNotNull(response);
        assertSpeech(response, "hello");
        assertExpectUserResponseFalse(response);
        assertNotNullRichResponse(response);
        assertTextToSpeech(response, "hello");
        assertDisplayText(response, "hi");

//        {
//            "speech": "hello",
//            "data": {
//            "google": {
//                "expect_user_response": false,
//                "rich_response": {
//                    "items": [{
//                        "simple_response": {
//                        "text_to_speech": "hello",
//                        "display_text": "hi"
//                     }
//                  }],
//                  "suggestions": [{
//                      "title": "Say this"
//                  },
//                  {
//                      "title": "or this"
//                  }]
//                }
//            }
//          },
//            "contextOut": []
//        }

        response = app.tell(app.buildRichResponse()
                .addSimpleResponse(new SimpleResponse("hello", "", "hi"))
                .addSuggestions(new String [] {"say this", "say that"}));

        assertNotNull(response);
        assertSpeech(response, "hello");
        assertExpectUserResponseFalse(response);
        assertNotNullRichResponse(response);
        assertTextToSpeech(response, "hello");
        assertDisplayText(response, "hi");
        assertSuggestions(response, "say this", "say that");

        response = app.tell(app.buildRichResponse()
                .addSimpleResponse(new SimpleResponse("hello", "", "hi"))
                .addSuggestions(suggestions("say this", "say that")));

        assertNotNull(response);
        assertSpeech(response, "hello");
        assertExpectUserResponseFalse(response);
        assertNotNullRichResponse(response);
        assertTextToSpeech(response, "hello");
        assertDisplayText(response, "hi");
        assertSuggestions(response, "say this", "say that");

    }

    /**
     * Describes the behavior for ApiAiApp ask method.
     */
    @Test
    public void appAskTest() {
        Response response;

        response = app.ask("hello");
        // {
        //   'speech': 'hello',
        //   'data': {
        //     'google': {
        //       'expect_user_response': true,
        //       'is_ssml': false,
        //       'no_input_prompts': []
        //     }
        //   },
        //   'contextOut': [
        //     {
        //       'name': '_actions_on_google_',
        //       'lifespan': 100,
        //       'parameters': {}
        //     }
        //   ]
        // }

        assertNotNull(response);
        assertSpeech(response, "hello");
        assertExpectUserResponseTrue(response);
        assertIsSsmlFalse(response);

        response = app.ask("hello", "hi");
        //  {
        //   'speech': 'hello',
        //   'data': {
        //     'google': {
        //       'expect_user_response': true,
        //       'rich_response': {
        //         'items': [
        //           {
        //             'simple_response': {
        //               'text_to_speech': 'hello',
        //               'display_text': 'hi'
        //             }
        //           }
        //         ],
        //         'suggestions': []
        //       }
        //     }
        //   },
        //   'contextOut': [
        //     {
        //       'name': '_actions_on_google_',
        //       'lifespan': 100,
        //       'parameters': {}
        //     }
        //   ]
        // };

        assertNotNull(response);
        assertSpeech(response,"hello");
        assertExpectUserResponseTrue(response);
        assertNotNullRichResponse(response);
        assertTextToSpeech(response, "hello");
        assertDisplayText(response, "hi");

        response = app.ask(app.buildRichResponse()
                .addSimpleResponse(new SimpleResponse("hello", "","hi"))
                .addSuggestions(Arrays.asList("say this", "or this")));

        //  {
        //   'speech': 'hello',
        //   'data': {
        //     'google': {
        //       'expect_user_response': true,
        //       'rich_response': {
        //         'items': [
        //           {
        //             'simple_response': {
        //               'text_to_speech': 'hello',
        //               'display_text': 'hi'
        //             }
        //           }
        //         ],
        //         'suggestions': [
        //           {
        //             'title': 'Say this'
        //           },
        //           {
        //             'title': 'or this'
        //           }
        //         ]
        //       }
        //     }
        //   },
        //   'contextOut': [
        //     {
        //       'name': '_actions_on_google_',
        //       'lifespan': 100,
        //       'parameters': {}
        //     }
        //   ]
        // }

        assertNotNull(response);
        assertSpeech(response, "hello");
        assertExpectUserResponseTrue(response);
        assertNotNullRichResponse(response);
        assertTextToSpeech(response,"hello");
        assertDisplayText(response, "hi");
        assertSuggestions(response, "say this", "or this");

    }

    private void assertSpeech(Response response, String speech) {
        assertEquals(speech, response.getSpeech());
    }

    private void assertExpectUserResponseFalse(Response response) {
        assertEquals(false, response.getData().getGoogle().expectUserResponse);
    }
    private void assertExpectUserResponseTrue(Response response) {
        assertEquals(true, response.getData().getGoogle().expectUserResponse);
    }

    private void assertIsSsmlFalse(Response response) {
        assertEquals(false, response.getData().getGoogle().isSsml);
    }

    private void assertNotNullRichResponse(Response response) {
        assertNotNull(response.getData().getGoogle().getRichResponse());
        assertNotNull(response.getData().getGoogle().getRichResponse().getItems());
        assertNotNull(response.getData().getGoogle().getRichResponse().getItems().get(0).getSimpleResponse());
        assertNotNull(response.getData().getGoogle().getRichResponse().getSuggestions());

    }

    private void assertTextToSpeech(Response response, String speech) {
        assertEquals(speech, response.getData().getGoogle().getRichResponse().getItems().get(0).getSimpleResponse().getTextToSpeech());
    }

    private void assertDisplayText(Response response, String displayText) {
        assertEquals(displayText, response.getData().getGoogle().getRichResponse().getItems().get(0).getSimpleResponse().getDisplayText());
    }

    private void assertSsmlText(Response response, String ssmlText) {
        assertEquals(ssmlText, response.getData().getGoogle().getRichResponse().getItems().get(0).getSimpleResponse().getSsml());
    }

    private void assertSuggestions(Response response, String ...suggestions) {
        List<Suggestion> suggestionList = response.getData().getGoogle().getRichResponse().getSuggestions();
        for (int i=0; i< suggestions.length; i++) {
            assertEquals(suggestions[i], suggestionList.get(i).getTitle());
        }
    }

    private List<Suggestion> suggestions(String ... strings) {
        List<Suggestion> suggestions = new ArrayList<>();
        for (String suggestion : strings) {
            suggestions.add(new Suggestion(suggestion));
        }
        return suggestions;
    }
}
