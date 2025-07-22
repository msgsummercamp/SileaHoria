const dogImage: HTMLImageElement | null = document.querySelector("#dog-image");
const loadingElement: HTMLElement | null = document.querySelector("#loading");
const errorElement: HTMLElement | null = document.querySelector("#error");

async function loadRandomDogFetch(): Promise<void> {
  if (!dogImage || !loadingElement || !errorElement) {
    console.error("Required elements not found in the document.");
    return;
  }

  loadingElement.style.display = "block";
  dogImage.style.display = "none";
  errorElement.style.display = "none";

  const response: Response | void = await fetch(
    "https://dog.ceo/api/breeds/image/random"
  ).catch((_) => {
    loadingElement.style.display = "none";
    errorElement.style.display = "block";
  });

  if (!response) {
    return;
  }

  const data: { message: string } = await response.json();

  dogImage.src = data.message;

  loadingElement.style.display = "none";
  dogImage.style.display = "block";
}
