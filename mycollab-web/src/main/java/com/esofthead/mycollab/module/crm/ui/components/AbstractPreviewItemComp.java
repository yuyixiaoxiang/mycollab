/**
 * This file is part of mycollab-web.
 *
 * mycollab-web is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * mycollab-web is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with mycollab-web.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.esofthead.mycollab.module.crm.ui.components;

import com.esofthead.mycollab.vaadin.mvp.AbstractPageView;
import com.esofthead.mycollab.vaadin.ui.AbstractBeanFieldGroupViewFieldFactory;
import com.esofthead.mycollab.vaadin.ui.AddViewLayout2;
import com.esofthead.mycollab.vaadin.ui.AdvancedPreviewBeanForm;
import com.esofthead.mycollab.vaadin.ui.IFormLayoutFactory;
import com.esofthead.mycollab.vaadin.ui.VerticalTabsheet;
import com.vaadin.server.Resource;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.TabSheet.Tab;

/**
 * 
 * @author MyCollab Ltd.
 * @since 3.0
 * 
 * @param <B>
 */
public abstract class AbstractPreviewItemComp<B> extends AbstractPageView {
	private static final long serialVersionUID = 1L;

	protected B beanItem;
	protected AddViewLayout2 previewLayout;
	protected AdvancedPreviewBeanForm<B> previewForm;
	protected VerticalTabsheet previewItemContainer;

	public AbstractPreviewItemComp(Resource iconResource) {
		previewItemContainer = new VerticalTabsheet(false);

		this.addComponent(previewItemContainer);
		previewItemContainer.setSizeFull();
		previewItemContainer.setNavigatorWidth("100%");
		previewItemContainer.setNavigatorStyleName("sidebar-menu");
		previewItemContainer.setContainerStyleName("tab-content");
		previewItemContainer.setHeight(null);

		previewItemContainer
				.addSelectedTabChangeListener(new SelectedTabChangeListener() {
					private static final long serialVersionUID = 1L;

					@Override
					public void selectedTabChange(SelectedTabChangeEvent event) {
						Tab tab = ((VerticalTabsheet) event.getSource())
								.getSelectedTab();
						previewItemContainer.selectTab(tab.getCaption());
					}
				});

		previewLayout = new AddViewLayout2("", iconResource);

		initRelatedComponents();

		previewForm = initPreviewForm();
		previewLayout.setStyleName("readview-layout");

		VerticalLayout informationLayout = new VerticalLayout();
		informationLayout.addStyleName("main-info");
		ComponentContainer actionControls = createButtonControls();
		if (actionControls != null) {
			actionControls.addStyleName("control-buttons");
			informationLayout.addComponent(actionControls);
		}

		informationLayout.addComponent(previewForm);
		previewLayout.addBody(informationLayout);
		previewLayout.addBody(createBottomPanel());
	}

	public void previewItem(final B item) {
		this.beanItem = item;
		previewLayout.setTitle(initFormTitle());

		previewForm.setFormLayoutFactory(initFormLayoutFactory());
		previewForm.setBeanFormFieldFactory(initBeanFormFieldFactory());
		previewForm.setBean(item);

		onPreviewItem();
	}

	public B getBeanItem() {
		return beanItem;
	}

	public AdvancedPreviewBeanForm<B> getPreviewForm() {
		return previewForm;
	}

	abstract protected void onPreviewItem();

	abstract protected String initFormTitle();

	abstract protected AdvancedPreviewBeanForm<B> initPreviewForm();

	abstract protected void initRelatedComponents();

	abstract protected IFormLayoutFactory initFormLayoutFactory();

	abstract protected AbstractBeanFieldGroupViewFieldFactory<B> initBeanFormFieldFactory();

	abstract protected ComponentContainer createButtonControls();

	abstract protected ComponentContainer createBottomPanel();

}