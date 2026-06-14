/*
 * Landing page support JavaScript.
 * @author Joseph Burris
 */

let openModal = null;
let modalBackdrop = null;

function closeModal() {
	if (!openModal) {
		return;
	}

	openModal.classList.remove('show');
	openModal.style.display = 'none';
	openModal.setAttribute('aria-hidden', 'true');
	openModal.removeAttribute('aria-modal');
	document.body.classList.remove('modal-open');

	if (modalBackdrop) {
		modalBackdrop.remove();
		modalBackdrop = null;
	}

	openModal = null;
}

function showModal(modal) {
	openModal = modal;
	modal.removeAttribute('aria-hidden');
	modal.setAttribute('aria-modal', 'true');
	modal.style.display = 'block';
	modal.classList.add('show');
	document.body.classList.add('modal-open');

	modalBackdrop = document.createElement('div');
	modalBackdrop.className = 'modal-backdrop fade show';
	document.body.appendChild(modalBackdrop);
	modalBackdrop.addEventListener('click', closeModal);
}

document.addEventListener('click', function(event) {
	let modalTrigger = event.target.closest('[data-bs-toggle="modal"][data-bs-target]');
	if (modalTrigger) {
		let modal = document.querySelector(modalTrigger.getAttribute('data-bs-target'));
		if (modal) {
			event.preventDefault();
			showModal(modal);
		}
		return;
	}

	if (event.target.closest('[data-bs-dismiss="modal"]')) {
		event.preventDefault();
		closeModal();
	}
});

document.addEventListener('keydown', function(event) {
	if (event.key === 'Escape') {
		closeModal();
	}
});
