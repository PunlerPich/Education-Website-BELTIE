/* eslint-disable no-undef */
import React from "react";
export const ItemCounter = ({ number }) => (
  <div className="flex items-center gap-1 text-default-400">
    <span className="text-small">{number}</span>
    // eslint-disable-next-line no-undef
    <ChevronRightIcon className="text-xl" />
  </div>
);
