const dogImage: HTMLImageElement | null  = document.querySelector<HTMLImageElement>(".dog-image" );
const loadingElement: HTMLElement | null = document.querySelector<HTMLElement>(".loading");
const errorElement: HTMLElement | null = document.querySelector<HTMLElement>(".error-message");

async function loadRandomDogFetch(): Promise<void> {
  if (!dogImage || !loadingElement || !errorElement) {
    console.error("Required elements not found in the document.");
    return;
  }

  loadingElement.classList.remove('hidden');
  dogImage.classList.add('hidden');
  errorElement.classList.add('hidden');

  await fetch("https://dog.ceo/api/breeds/image/random")
    .then((response) => {
      if (response.ok) {
        return response.json();
      }
      throw new Error("Something went wrong")
    })
    .then(data => {
      dogImage.src = data.message;
      loadingElement.classList.add('hidden');
      dogImage.classList.remove('hidden');
    })
    .catch((error) => {
        loadingElement.classList.add('hidden');
        errorElement.classList.remove('hidden');
        errorElement.textContent = error.message;
    });

}
