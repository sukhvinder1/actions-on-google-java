package ca.sukhsingh.actions.on.google;

import ca.sukhsingh.actions.on.google.response.Response;
import ca.sukhsingh.actions.on.google.response.data.google.richresponse.SimpleResponse;
import ca.sukhsingh.actions.on.google.response.data.google.richresponse.Suggestion;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by sukhsingh on 2017-08-29.
 */
@RunWith(MockitoJUnitRunner.class)
public class ApiAiAppTest {

    @InjectMocks
    private ApiAiApp app;

    @Test
    public void appTellWithString() {
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

    }

    @Test
    public void appTellWithTextToSpeechAndDisplayText() {

        //        {
//            'speech': 'hello',
//            'data': {
//            'google': {
//                'expect_userresponse': false,
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

        Response response = app.tell("hello", "hi");
        assertNotNull(response);
        assertSpeech(response, "hello");
        assertExpectUserResponseFalse(response);
        assertNotNullRichResponse(response);
        assertTextToSpeech(response, "hello");
        assertDisplayText(response, "hi");

    }

    @Test
    public void appTellWithSSMLString() {
        final String SPEECH = "<speak>hello</speak>";
        Response response = app.tell(SPEECH);
        assertNotNull(response);
        assertSpeech(response, SPEECH);
        assertIsSsmlTrue(response);
    }

    @Test
    public void appTellWithTextToSpeechSSMLAndDisplayText() {
// {
//     "contextOut": [],
//     "data": {
//         "google": {
//             "expectUserResponse": false,
//                 "richResponse": {
//                 "items": [{
//                     "simpleResponse": {
//                         "ssml": "<speak>hello</speak>",
//                         "displayText": "simple"
//                     }
//                 }],
//                     "suggestions": []
//             }
//         }
//     }
// }

        final String SPEECH = "<speak>Hello</speak>";
        final String DISPLAYTEXT = "Hi";
        Response response = app.tell(SPEECH, DISPLAYTEXT);
        assertNotNull(response);
        assertSsmlText(response, SPEECH);
        assertDisplayText(response, DISPLAYTEXT);
        assertExpectUserResponseFalse(response);
    }

    @Test
    public void appTellWithSimpleResponse() {
        final String SPEECH = "<speak>Hello</speak>";
        final String DISPLAYTEXT = "Hi";
        SimpleResponse simpleResponse = new SimpleResponse(SPEECH, DISPLAYTEXT);
        Response response = app.tell(simpleResponse);
        assertNotNull(response);
        assertExpectUserResponseFalse(response);
        assertTextToSpeech(response,null);
        assertSsmlText(response,SPEECH);
        assertDisplayText(response,DISPLAYTEXT);
    }

    @Test
    public void appTellWithRichResponse() {

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

        Response response = app.tell(app.buildRichResponse()
                .addSimpleResponse("hello", "hi")
                .addSuggestions(new String [] {"say this", "say that"}));

        assertNotNull(response);
        assertSpeech(response, "hello");
        assertExpectUserResponseFalse(response);
        assertNotNullRichResponse(response);
        assertTextToSpeech(response, "hello");
        assertDisplayText(response, "hi");
        assertSuggestions(response, "say this", "say that");

        response = app.tell(app.buildRichResponse()
                .addSimpleResponse(new SimpleResponse("hello", "hi"))
                .addSuggestions(suggestions("say this", "say that")));

        assertNotNull(response);
        assertSpeech(response, "hello");
        assertExpectUserResponseFalse(response);
        assertNotNullRichResponse(response);
        assertTextToSpeech(response, "hello");
        assertDisplayText(response, "hi");
        assertSuggestions(response, "say this", "say that");

    }

    @Test
    public void appTellWithOneNullParam() {
        Response response = app.tell("");
        assertNull(response);
    }

    @Test
    public void appTellWithTwoNullParams() {
        Response response = app.tell(null,null);
        assertNull(response);
    }

    @Test
    public void appTellWithStringAndNullParams() {
        Response response = app.tell("hi", null);
        assertNull(response);
    }

    @Test
    public void appTellWithNullAndStringParams() {
        Response response = app.tell(null, "hi");
        assertNull(response);
    }


    //********* APP ASK **********

    @Test
    public void appAskWithString() {

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

    }

    @Test
    public void appAskWithTextToSpeechAndDisplayText() {
        Response response = app.ask("hello", "hi");
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
        //       'name': '_actions_ongoogle_',
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

    }

    @Test
    public void appAskWithSimpleResponse() {
        final String SPEECH = "Hello";
        final String DISPLAYTEXT = "Hi";
        SimpleResponse simpleResponse = new SimpleResponse(SPEECH, DISPLAYTEXT);
        Response response = app.ask(simpleResponse);
        assertNotNull(response);
        assertTextToSpeech(response, SPEECH);
        assertDisplayText(response, DISPLAYTEXT);
        assertExpectUserResponseTrue(response);

    }

    @Test
    public void appAskWithRichResponse() {

        Response response = app.ask(app.buildRichResponse()
                .addSimpleResponse(new SimpleResponse("hello","hi"))
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

    @Test
    public void appAskWithssml() {
        final String SPEECH = "<speak>hello</speak>";
        Response response = app.ask(SPEECH);
        assertNotNull(response);
        assertIsSsmlTrue(response);
        assertExpectUserResponseTrue(response);
        assertSpeech(response, SPEECH);
    }

    @Test
    public void appAskWithOneNullParam() {
        Response response = app.ask("");
        assertNull(response);
    }

    @Test
    public void appAskWithTwoNullParams() {
        Response response = app.ask("", "");
        assertNull(response);
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

    private void assertIsSsmlTrue(Response response) {
        assertEquals(true, response.getData().getGoogle().isSsml);
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
