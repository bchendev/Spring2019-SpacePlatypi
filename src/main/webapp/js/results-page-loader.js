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
const parameterUsername = urlParams.get('User');

// URL must include ?user=XYZ parameter. If not, redirect to homepage.
if (!parameterUsername) {
  window.location.replace('/');
}

/** Sets the page title based on the URL parameter username. */
function setPageTitle() {
  document.getElementById('page-title').innerText = parameterUsername;
  document.title = parameterUsername + ' - Search Results';
}

/** Fetches Users and add them to the page. */
function fetchUsers() {
  const url = '/search';
  fetch(url)
      .then((response) => {
        return response.json();
      })
      .then((User) => {
        const userContainer = document.getElementById('User');
        if (User.length == 0) {
          userContainer.innerHTML = '<p>No Results Found.</p>';
        } else {
          userContainer.innerHTML = '';
        }
        user.forEach((User) => {
          const userDiv = buildUserDiv(user);
          userContainer.appendChild(userDiv);
        });
      });
}

/**
 * Builds an element that displays the message.
 * @param {Message} message
 * @return {Element}
 */
function buildUserDiv(user) {
  const headerDiv = document.createElement('div');
  headerDiv.classList.add('user-header');

  const bodyDiv = document.createElement('div');
  bodyDiv.classList.add('user-body');
  bodyDiv.innerHTML = message.text;

  const messageDiv = document.createElement('div');
  messageDiv.classList.add('user-div');
  messageDiv.appendChild(headerDiv);
  messageDiv.appendChild(bodyDiv);

  return messageDiv;
}

/** Fetches data and populates the UI of the page. */
function buildUI() {
  setPageTitle();
  showMessageFormIfViewingSelf();
  fetchMessages();
}