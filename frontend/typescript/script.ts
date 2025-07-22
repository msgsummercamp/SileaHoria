const dogImage : HTMLImageElement | null = document.getElementById('dog-image') as HTMLImageElement | null;
const loadingElement : HTMLElement  | null = document.getElementById('loading');

async function loadRandomDogFetch() : Promise<void> {
    if (!dogImage || !loadingElement) {
        console.error('Required elements not found in the document.');
        return;
    }

    try {
        loadingElement.style.display = 'block';
        dogImage.style.display = 'none';

        const response : Response = await fetch('https://dog.ceo/api/breeds/image/random');
        const data : { message: string } = await response.json();

        if (!response.ok) {
            console.error(`HTTP error! status: ${response.status}`);
            loadingElement.style.display = 'none';
            return;
        }

        dogImage.src = data.message;

        loadingElement.style.display = 'none';
        dogImage.style.display = 'block';
    } catch (error) {
        console.error('Error fetching dog image:', error);
        loadingElement.style.display = 'none';
    }
}
