/*
 * Copyright 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// Get ?user=XYZ parameter value
const urlParams = new URLSearchParams(window.location.search);
const parameterUsername = urlParams.get('user');

/** Sets the page title based on the URL parameter username. */
function setPageTitle() {
  document.getElementById('page-title').innerText = parameterUsername;
  document.title = parameterUsername + ' - User Page';
}

/**
* Shows the message form if the user is logged in.
*/
function showMessageFormIfLoggedIn() {
  fetch('/login-status')
      .then((response) => {
        return response.json();
      })
      .then((loginStatus) => {
        if (loginStatus.isLoggedIn) {
          const messageForm = document.getElementById('message-form');
          messageForm.action = '/messages?recipient=' + parameterUsername;
          messageForm.classList.remove('hidden');
          document.getElementById('about-me-form').classList.remove('hidden');
        }
      });
}

/** Fetches messages and add them to the page. */
function fetchMessages() {
  const url = '/messages?user=' + parameterUsername;
  fetch(url)
      .then((response) => {
        return response.json();
      })
      .then((messages) => {
        const messagesContainer = document.getElementById('message-container');
        if (messages.length == 0) {
          messagesContainer.innerHTML = '<p>This user has no posts yet.</p>';
        } else {
          messagesContainer.innerHTML = '';
        }
        messages.forEach((message) => {
          const messageDiv = buildMessageDiv(message);
          messagesContainer.appendChild(messageDiv);
        });
      });
}

/**
 * Builds action after getting URL from servlet
 * @return text
 */
function fetchImageUploadUrlAndShowForm() {
  fetch('/image-upload-url')
      .then((response) => {
        return response.text();
      })
      .then((imageUploadUrl) => {
        const user = document.getElementById('profilePicture');
        user.src = imageUploadUrl;
        user.classList.remove('hidden');
      });
}

/**
 * Builds an element that displays the message.
 * @param {Message} message
 * @return {Element}
 */
function buildMessageDiv(message) {
  const headerDiv = document.createElement('div');
  headerDiv.classList.add('message-header');
  headerDiv.appendChild(document.createTextNode(
      message.user + ' - ' +
      new Date(message.timestamp) + 
      ' [' + message.sentimentScore + ']'));

  const bodyDiv = document.createElement('div');
  bodyDiv.classList.add('message-body');
  bodyDiv.innerHTML = message.text;

  const messageDiv = document.createElement('div');
  messageDiv.classList.add('message-div');
  messageDiv.appendChild(headerDiv);
  messageDiv.appendChild(bodyDiv);

  return messageDiv;
}

/** Fetches user data then adds it to the page */
function fetchAboutMe(){
  const url = "/about?user=" + parameterUsername;
  fetch(url)
    .then(response => {
      return response.json();
    })
    .then(jsonObject => {
      // About Me
      const aboutMeContainer = document.getElementById("about-me-container");
      const aboutMeInput = document.getElementById("about-me-input");
      if (jsonObject.aboutMe == null || jsonObject.aboutMe == "") {
        jsonObject.aboutMe = "This user has not entered any information yet.";
      }

      aboutMeContainer.innerHTML = jsonObject.aboutMe;
      aboutMeInput.innerHTML = jsonObject.aboutMe;
      buildClassicEditor();

      // Location
      const address = document.getElementById("Address");
      if (jsonObject.location) {
        address.value = jsonObject.location;
        geocodeMap(jsonObject.location);
      }
  });
}

/** Builds the classic editor for the about me seciton. */
function buildClassicEditor() {
  ClassicEditor.create(document.querySelector("#about-me-input"), {
    removePlugins: ["BlockQuote "],
    // Image upload feature to be incorporated with image project
    toolbar: [
      "heading",
      "|",
      "bold",
      "italic",
      "link",
      "bulletedList",
      "numberedList",
      "imageUpload",
      "undo",
      "redo"
    ],
    heading: {
      options: [
        {
          model: "paragraph",
          title: "Paragraph",
          class: "ck-heading_paragraph"
        },
        {
          model: "heading1",
          view: "h1",
          title: "Title",
          class: "ck-heading_heading1"
        },
        {
          model: "heading2",
          view: "h2",
          title: "Heading",
          class: "ck-heading_heading2"
        },
        {
          model: "heading3",
          view: "h3",
          title: "Subheading",
          class: "ck-heading_heading3"
        }
      ]
    }
  });
}

function submitInfo() {
  const address = document.getElementById("Address");
  if (address.value) {
    const url = "/about?location=" + address.value;
    fetch(url, {
      method: "POST"
    });
  }
}

function geocodeMap(address) {
  var map = new google.maps.Map(document.getElementById("map"), {
    zoom: 8,
    center: { lat: -34.397, lng: 150.644 }
  });
  
  var geocoder = new google.maps.Geocoder();
  geocodeAddress(geocoder, map, address);
}

function geocodeAddress(geocoder, resultsMap, addr) {
  geocoder.geocode({ address: addr }, function(results, status) {
    if (status === "OK") {
      resultsMap.setCenter(results[0].geometry.location);
      resultsMap.setZoom(15);
      var marker = new google.maps.Marker({
        map: resultsMap,
        position: results[0].geometry.location
      });

      var formattedAddress = results[0].formatted_address;
      var infoWindow = new google.maps.InfoWindow({
        content: formattedAddress
      });
      marker.addListener("click", () => {
        infoWindow.open(resultsMap, marker);
      });
    }
  });
}

/** Fetches data and populates the UI of the page. */
function buildUI() {
  setPageTitle();
  fetchAboutMe();
  showMessageFormIfLoggedIn();
  fetchMessages();
}