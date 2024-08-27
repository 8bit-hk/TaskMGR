// サイドバーのアコーディオンメニューを開く処理（アニメーション付き）
document.addEventListener('DOMContentLoaded', () => {

	const details = document.querySelectorAll('.details');

	details.forEach(element => {
		const summary = element.querySelector('.details__summary');
		const content = element.querySelector('.details__content');

	    summary.addEventListener('click', e => {
			e.preventDefault();
			if (element.open) {
        		const openDetails = content.animate(
					{
						opacity: [1, 0],
	            		height: [content.offsetHeight + 'px', 0],
	          		},
	          		{
	            		duration: 360,
	            		easing: 'ease-out',
	          		}	
        		);
        		openDetails.onfinish = () => {
          		element.removeAttribute('open');
        		}
			} else {
				element.setAttribute('open', 'true');
				const openDetails = content.animate(
					{
						opacity: [0, 1],
						height: [0, content.offsetHeight + 'px'],
					},
					{
						duration: 360,
						easing: 'ease-out',
					}
				);
			}
		});
	});
});