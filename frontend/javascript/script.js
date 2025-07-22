const dogImage = document.getElementById("dog-image");
const loadingElement = document.getElementById("loading");
const errorElement = document.getElementById("error-message");

async function loadRandomDogFetch() {
  try {
    loadingElement.style.display = "block";
    errorElement.style.display = "none";
    dogImage.style.display = "none";

    const response = await fetch("https://dog.ceo/api/breeds/image/random");
    const data = await response.json();
    dogImage.src = data.message;

    loadingElement.style.display = "none";
    dogImage.style.display = "block";
  } catch (error) {
    errorElement.style.display = "block";
    loadingElement.style.display = "none";
  }
}
