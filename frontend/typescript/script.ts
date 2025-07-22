const dogImage: HTMLImageElement | null = document.querySelector("#dog-image");
const loadingElement: HTMLElement | null = document.querySelector("#loading");
const errorElement: HTMLElement | null = document.querySelector("#error");

async function loadRandomDogFetch(): Promise<void> {
  if (!dogImage || !loadingElement || !errorElement) {
    console.error("Required elements not found in the document.");
    return;
  }

  loadingElement.classList.remove('hidden');
  dogImage.classList.add('hidden');
  errorElement.classList.add('hidden');

  const response: Response | void = await fetch(
    "https://dog.ceo/api/breeds/image/random"
  ).catch((_) => {
    loadingElement.classList.add('hidden');
    errorElement.classList.remove('hidden');
  });

  if (!response) {
    return;
  }

  const data: { message: string } = await response.json();

  dogImage.src = data.message;

  loadingElement.classList.add('hidden')
  dogImage.classList.remove('hidden');
}
