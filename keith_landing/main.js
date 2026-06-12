/*
 * Landing page support JavaScript.
 * @author Joseph Burris
 */

let activePopover = null;

function hidePopover() {
	if (activePopover) {
		activePopover.remove();
		activePopover = null;
	}
}

document.querySelectorAll('[data-bs-toggle="popover"]').forEach(function(trigger) {
	trigger.addEventListener('click', function(event) {
		event.preventDefault();
		hidePopover();

		let popover = document.createElement('div');
		let title = trigger.getAttribute('title');
		let content = trigger.getAttribute('data-bs-content');
		let rect = trigger.getBoundingClientRect();

		popover.className = 'site-popover';
		popover.innerHTML = '<strong></strong><p></p>';
		popover.querySelector('strong').textContent = title || '';
		popover.querySelector('p').textContent = content || '';
		document.body.appendChild(popover);

		let popoverRect = popover.getBoundingClientRect();
		popover.style.left = Math.max(16, rect.left + window.scrollX - (popoverRect.width / 2) + (rect.width / 2)) + 'px';
		popover.style.top = (rect.bottom + window.scrollY + 8) + 'px';
		activePopover = popover;
	});

	trigger.addEventListener('blur', function() {
		window.setTimeout(hidePopover, 150);
	});
});

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
		hidePopover();
		closeModal();
	}
});
